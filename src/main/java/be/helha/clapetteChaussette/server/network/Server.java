package be.helha.clapetteChaussette.server.network;

import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.network.ObjectSocket;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.common.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuestionRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuizzRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcTeacherRepository;
import be.helha.clapetteChaussette.server.utils.CodeGenerator;
import be.helha.clapetteChaussette.client.service.repository.TeacherService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the server for the Clapette Chaussette application.
 * <p>
 * This class is responsible for initializing the {@link ServerSocket}
 * and listening for incoming student connections.
 * </p>
 */
public class Server implements ClientThreadHandler.Listener {

    private static final int SERVER_PORT = 3001;

    private final List<ClientThreadHandler> clientHandlerThreads = new ArrayList<>();

    private final TeacherService teacherService = new TeacherService(new JdbcTeacherRepository());

    private final IQuizzRepository quizzRepository = new JdbcQuizzRepository();

    private final IQuestionRepository questionRepository = new JdbcQuestionRepository();

    /** Active quizz sessions, indexed by their invitation code. */
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    /**
     * Starts the server and enters an infinite loop to accept multiple students.
     * <p>
     * The {@link ServerSocket#accept()} method is used to block execution
     * until a client connects.
     * </p>
     * 
     * @throws IOException if a network communication error occurs during the socket
     *                     creation
     *                     or while accepting a connection.
     */
    public void startServer() {
        System.out.println("Starting server...");
        // Use try-with-resources to ensure the ServerSocket is closed automatically
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started on port " + SERVER_PORT + ". Waiting for connections...");

            while (true) {
                // Wait for a student to connect (blocking method)
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress());
                ObjectSocket objectSocket = new ObjectSocket(socket);

                ClientThreadHandler thread = new ClientThreadHandler(objectSocket, this);
                this.clientHandlerThreads.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Server network error: " + e.getMessage());
        }
    }

    @Override
    public void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest) {
        String name = loginRequest.getTeacher().getName();
        try {
            // Le mot de passe en clair est vérifié ici via BCrypt (hashage côté serveur).
            Teacher teacher = teacherService.teacherLogin(name, loginRequest.getPassword());
            System.out.println("Connecté avec " + teacher.getName());

            List<QuizzDTO> quizzes = quizzRepository.findAll(teacher.getUuid());
            reply(clientThreadHandler, new LoginResponse(true, teacher, quizzes, "OK"));
        } catch (LoginFailedException e) {
            System.out.println("Échec de connexion pour " + name);
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Identifiants invalides"));
        }
    }

    /**
     * Sends a response back to the client, logging any network error.
     */
    private void reply(ClientThreadHandler clientThreadHandler, LoginResponse response) {
        try {
            clientThreadHandler.send(response);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer la réponse au client : " + e.getMessage());
        }
    }

    @Override
    public void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest) {
        String name = registerRequest.getTeacher().getName();
        try {
            // Le serveur hashe le mot de passe (BCrypt) avant de le persister.
            Teacher teacher = teacherService.registerNewTeacher(name, registerRequest.getPassword());
            System.out.println("Nouveau prof créé : " + teacher.getName());

            // On répond comme pour un login réussi afin que le client bascule d'écran.
            List<QuizzDTO> quizzes = quizzRepository.findAll(teacher.getUuid());
            reply(clientThreadHandler, new LoginResponse(true, teacher, quizzes, "OK"));
        } catch (TeacherAlreadyExistsException e) {
            System.out.println("Le prof existe déjà : " + name);
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Ce professeur existe déjà"));
        } catch (IllegalArgumentException e) {
            System.out.println("Données d'inscription invalides : " + e.getMessage());
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Données d'inscription invalides"));
        }
    }

    @Override
    public void onSaveQuizz(ClientThreadHandler clientThreadHandler, SaveQuizzRequest request) {
        QuizzDTO quizz = request.getQuizz();
        try {
            quizzRepository.save(quizz);
            questionRepository.saveQuestions(request.getQuestions());
            System.out.println("Questionnaire enregistré : " + quizz.name());

            // Return the refreshed list so the teacher's screen can update.
            List<QuizzDTO> quizzes = quizzRepository.findAll(quizz.teacherUuid());
            reply(clientThreadHandler, new SaveQuizzResponse(true, "OK", quizzes));
        } catch (DatabaseException e) {
            System.err.println("Échec de l'enregistrement du questionnaire : " + e.getMessage());
            reply(clientThreadHandler,
                    new SaveQuizzResponse(false, "Impossible d'enregistrer le questionnaire", List.of()));
        }
    }

    /**
     * Sends a save-quizz response back to the client, logging any network error.
     */
    private void reply(ClientThreadHandler clientThreadHandler, SaveQuizzResponse response) {
        try {
            clientThreadHandler.send(response);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer la réponse au client : " + e.getMessage());
        }
    }

    @Override
    public void onLaunchQuizz(ClientThreadHandler clientThreadHandler, LaunchQuizzRequest request) {
        QuizzDTO quizz = request.getQuizz();

        // Generate a unique code and create the session tied to the teacher's
        // connection.
        String code = generateUniqueCode();
        Session session = new Session(code, quizz, clientThreadHandler);
        sessions.put(code, session);
        System.out.println("Session lancée : code " + code + " pour le questionnaire « " + quizz.name() + " »");

        reply(clientThreadHandler, new LaunchQuizzResponse(true, code, session.getNumberParticipants(), "OK"));
    }

    /**
     * Generates an invitation code not already used by an active session.
     */
    private String generateUniqueCode() {
        String code;
        do {
            code = CodeGenerator.generate();
        } while (sessions.containsKey(code));
        return code;
    }

    /**
     * Sends a launch-quizz response back to the client, logging any network error.
     */
    private void reply(ClientThreadHandler clientThreadHandler, LaunchQuizzResponse response) {
        try {
            clientThreadHandler.send(response);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer la réponse au client : " + e.getMessage());
        }
    }

    @Override
    public void onJoinSession(ClientThreadHandler clientThreadHandler, JoinSessionRequest request) {
        Session session = sessions.get(request.getCode());
        if (session == null) {
            reply(clientThreadHandler, new JoinSessionResponse(false, 0, "Code invalide"));
            return;
        }

        session.addClient(clientThreadHandler);
        int count = session.getNumberParticipants();
        System.out.println("Élève rejoint la session " + request.getCode() + " (" + count + " participant(s))");

        reply(clientThreadHandler, new JoinSessionResponse(true, count, "OK"));

        // Notify the teacher and the students already in the session.
        broadcastParticipantCount(session);
    }

    /**
     * Sends a join-session response back to the client, logging any network error.
     */
    private void reply(ClientThreadHandler clientThreadHandler, JoinSessionResponse response) {
        try {
            clientThreadHandler.send(response);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer la réponse au client : " + e.getMessage());
        }
    }

    /**
     * Notifies the teacher (host) and every student of the session's participant
     * count.
     */
    private void broadcastParticipantCount(Session session) {
        ParticipantCountUpdate update = new ParticipantCountUpdate(session.getNumberParticipants());
        send(session.getHost(), update);
        for (ClientThreadHandler client : session.getClients()) {
            send(client, update);
        }
    }

    private void send(ClientThreadHandler clientThreadHandler, Object event) {
        try {
            clientThreadHandler.send(event);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer un message au client : " + e.getMessage());
        }
    }

    @Override
    public void onLeaveSession(ClientThreadHandler clientThreadHandler, LeaveSessionRequest request) {
        removeFromSession(clientThreadHandler);
    }

    @Override
    public void onDisconnect(ClientThreadHandler clientThreadHandler) {
        // A student dropping out decrements the count; a teacher dropping ends the
        // session.
        removeFromSession(clientThreadHandler);
        sessions.values().removeIf(session -> session.getHost() == clientThreadHandler);
    }

    /**
     * Removes a student from its session (if any) and refreshes the count.
     */
    private void removeFromSession(ClientThreadHandler clientThreadHandler) {
        for (Session session : sessions.values()) {
            if (session.getClients().remove(clientThreadHandler)) {
                broadcastParticipantCount(session);
                break;
            }
        }
    }
}