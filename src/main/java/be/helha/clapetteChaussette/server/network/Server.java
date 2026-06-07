package be.helha.clapetteChaussette.server.network;

import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.AnswerReceivedEvent;
import be.helha.clapetteChaussette.common.models.events.NextQuestionRequest;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.SubmitAnswerRequest;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.RevealAnswerAskEvent;
import be.helha.clapetteChaussette.common.models.events.RevealAnswerEvent;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.config.AppConfig;
import be.helha.clapetteChaussette.common.network.ObjectSocket;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
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
import be.helha.clapetteChaussette.server.service.AuthService;
import be.helha.clapetteChaussette.server.service.QuizzService;
import be.helha.clapetteChaussette.server.service.ServerMessagingService;
import be.helha.clapetteChaussette.server.service.SessionManagementService;
import be.helha.clapetteChaussette.server.service.AnswerService;
import be.helha.clapetteChaussette.server.service.QuestionService;
import be.helha.clapetteChaussette.server.repository.TeacherService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the server for the Clapette Chaussette application.
 * <p>
 * This class is responsible for initializing the {@link ServerSocket}
 * and listening for incoming student connections.
 * </p>
 */
public class Server implements ClientThreadHandler.Listener {

    private final int serverPort = AppConfig.getInstance().getServerPort();

    private final List<Thread> listenerThreads = new ArrayList<>();

    private final TeacherService teacherService = new TeacherService(new JdbcTeacherRepository());

    private final IQuizzRepository quizzRepository = new JdbcQuizzRepository();

    private final IQuestionRepository questionRepository = new JdbcQuestionRepository();

    // import all our services
    private final ServerMessagingService messagingService = new ServerMessagingService(new QuestionService());
    private final SessionManagementService sessionManagementService = new SessionManagementService();
    private final AuthService authService = new AuthService(teacherService);
    private final QuizzService quizzService = new QuizzService(quizzRepository, questionRepository);
    private final AnswerService answerService = new AnswerService();

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
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server started on port " + serverPort + ". Waiting for connections...");

            while (true) {
                // Wait for a student to connect (blocking method)
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress());
                ObjectSocket objectSocket = new ObjectSocket(socket);

                ClientThreadHandler handler = new ClientThreadHandler(objectSocket, this);
                Thread listenerThread = new Thread(handler);
                this.listenerThreads.add(listenerThread);
                listenerThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server network error: " + e.getMessage());
        }
    }

    @Override
    public void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest) {
        String name = loginRequest.getTeacher().getName();
        String password = loginRequest.getPassword();
        try {
            Teacher teacher = authService.login(name, password);
            System.out.println("Prof connecté : " + teacher.getName());

            List<QuizzDTO> quizzes = quizzService.getTeacherQuizzes(teacher.getUuid());
            reply(clientThreadHandler, new LoginResponse(true, teacher, quizzes, "OK"));
        } catch (LoginFailedException e) {
            System.out.println("fail connection for : " + name);
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Identifiants invalides"));
        } catch (DatabaseException e) {
            System.err.println("Database error : " + e.getMessage());
            reply(clientThreadHandler,
                    new LoginResponse(false, null, List.of(), "Erreur serveur, réessayez plus tard"));
        }
    }

    /**
     * Sends a response back to the client, logging any network error.
     */
    private void reply(ClientThreadHandler clientThreadHandler, LoginResponse response) {
        try {
            clientThreadHandler.send(response);
        } catch (IOException e) {
            System.err.println("Impossible to send response : " + e.getMessage());
        }
    }

    @Override
    public void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest) {
        String name = registerRequest.getTeacher().getName();
        try {
            Teacher teacher = authService.register(name, registerRequest.getPassword());
            System.out.println("Nouveau prof créé : " + teacher.getName());

            List<QuizzDTO> quizzes = quizzService.getTeacherQuizzes(teacher.getUuid());
            reply(clientThreadHandler, new LoginResponse(true, teacher, quizzes, "OK"));
        } catch (TeacherAlreadyExistsException e) {
            System.out.println("Le prof existe déjà : " + name);
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Ce professeur existe déjà"));
        } catch (IllegalArgumentException e) {
            System.out.println("Données d'inscription invalides : " + e.getMessage());
            reply(clientThreadHandler, new LoginResponse(false, null, List.of(), "Données d'inscription invalides"));
        } catch (DatabaseException e) {
            System.err.println("Erreur base de données lors de l'inscription : " + e.getMessage());
            reply(clientThreadHandler,
                    new LoginResponse(false, null, List.of(), "Erreur serveur, réessayez plus tard"));
        }
    }

    @Override
    public void onSaveQuizz(ClientThreadHandler clientThreadHandler, SaveQuizzRequest request) {
        QuizzDTO quizz = request.getQuizz();
        try {
            quizzService.saveQuizz(quizz, request.getQuestions());
            System.out.println("Questionnaire enregistré : " + quizz.name());

            List<QuizzDTO> quizzes = quizzService.getTeacherQuizzes(quizz.teacherUuid());
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
        try {
            List<QuestionDTO> questions = quizzService.getQuizzQuestions(quizz.uuid());

            Session session = sessionManagementService.createSession(quizz, clientThreadHandler);
            session.loadQuestions(questions);
            System.out.println("Session lancée : code " + session.getCode() + " pour le questionnaire « " + quizz.name()
                    + " » (" + questions.size() + " questions)");

            reply(clientThreadHandler,
                    new LaunchQuizzResponse(true, session.getCode(), session.getNumberParticipants(), "OK", quizz));
        } catch (DatabaseException e) {
            System.err.println("Échec du chargement des questions : " + e.getMessage());
            reply(clientThreadHandler,
                    new LaunchQuizzResponse(false, null, 0, "Erreur serveur, réessayez plus tard", quizz));
        }
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
        boolean joined = sessionManagementService.addClientToSession(request.getCode(), clientThreadHandler,
                request.getPseudo());
        if (!joined) {
            reply(clientThreadHandler, new JoinSessionResponse(false, 0, "Code invalide"));
            return;
        }

        Optional<Session> sessionOpt = sessionManagementService.getSessionByCode(request.getCode());
        if (sessionOpt.isEmpty()) {
            reply(clientThreadHandler, new JoinSessionResponse(false, 0, "Code invalide"));
            return;
        }

        Session session = sessionOpt.get();
        int count = session.getNumberParticipants();
        System.out.println("Élève rejoint la session " + request.getCode() + " (" + count + " participant(s))");

        reply(clientThreadHandler, new JoinSessionResponse(true, count, "OK"));

        messagingService.broadcastParticipantUpdate(session);
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

    @Override
    public void onLeaveSession(ClientThreadHandler clientThreadHandler, LeaveSessionRequest request) {
        Optional<Session> sessionOpt = sessionManagementService.getSessionByClient(clientThreadHandler);
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            sessionManagementService.removeClientFromSession(clientThreadHandler);
            messagingService.broadcastParticipantUpdate(session);
        }
    }

    @Override
    public void onNextQuestion(ClientThreadHandler clientThreadHandler, NextQuestionRequest nextQuestionRequest) {
        Optional<Session> sessionOpt = sessionManagementService.getSessionByHost(clientThreadHandler);
        if (sessionOpt.isEmpty()) {
            messagingService.send(clientThreadHandler,
                    new NextQuestionResponse(false, "Session introuvable", null, -1));
            return;
        }

        Session session = sessionOpt.get();
        Optional<QuestionDTO> question = session.nextQuestion();
        if (question.isEmpty()) {
            messagingService.broadcastQuizzFinished(session, session.getCurrentIndex());
            return;
        }

        messagingService.broadcastNextQuestion(session, question.get(), session.getCurrentIndex());
    }

    @Override
    public void onSubmitAnswer(ClientThreadHandler clientThreadHandler, SubmitAnswerRequest request) {
        Optional<Session> sessionOpt = sessionManagementService.getSessionByClient(clientThreadHandler);
        if (sessionOpt.isEmpty()) {
            return;
        }
        Session session = sessionOpt.get();

        boolean answered = answerService.recordAnswer(session, clientThreadHandler, request.getQuestionUuid(),
                request.getQuestionType(), request.getAnswerIndex(), request.getAnswerText());
        if (!answered) {
            return;
        }

        messagingService.send(session.getHost(), new AnswerReceivedEvent(session.getAnsweredCount(),
                session.getNumberParticipants(), session.getVoteTotal(), session.getTextAnswerTotal()));
    }

    @Override
    public void onRevealAnswers(ClientThreadHandler clientThreadHandler, RevealAnswerAskEvent request) {
        Optional<Session> sessionOpt = sessionManagementService.getSessionByHost(clientThreadHandler);
        if (sessionOpt.isEmpty()) {
            return;
        }

        Session session = sessionOpt.get();
        Optional<QuestionDTO> current = session.getCurrentQuestion();
        if (current.isEmpty() || !current.get().uuid().equals(request.getQuestionUuid())) {
            return;
        }

        RevealAnswerEvent event = new RevealAnswerEvent(current.get(), session.getVoteTotal());
        messagingService.send(session.getHost(), event);
        for (ClientThreadHandler client : session.getClients()) {
            messagingService.send(client, event);
        }
    }

    @Override
    public void onDisconnect(ClientThreadHandler clientThreadHandler) {
        Optional<Session> studentSessionOpt = sessionManagementService.getSessionByClient(clientThreadHandler);
        if (studentSessionOpt.isPresent()) {
            Session session = studentSessionOpt.get();
            sessionManagementService.removeClientFromSession(clientThreadHandler);
            messagingService.broadcastParticipantUpdate(session);
        }

        sessionManagementService.removeSessionByHost(clientThreadHandler);
    }
}