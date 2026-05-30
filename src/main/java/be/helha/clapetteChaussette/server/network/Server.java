package be.helha.clapetteChaussette.server.network;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.network.ObjectSocket;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.common.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuestionRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuizzRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcTeacherRepository;
import be.helha.clapetteChaussette.client.service.repository.TeacherService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the server for the Clapette Chaussette application.
 * <p>
 * This class is responsible for initializing the {@link ServerSocket}
 * and listening for incoming student connections.
 * </p>
 */
public class Server implements ClientThreadHandler.Listener {

    /**
     * The listening port on which the server waits for connections.
     */
    private final int port;

    /**
     * Initializes a new instance of the server.
     * @param port The port number (e.g., 8080) on which the server should listen.
     */
    public Server(int port){
        this.port = port;
    }

    private final List<ClientThreadHandler> clientHandlerThreads = new ArrayList<>();

    private final TeacherService teacherService = new TeacherService(new JdbcTeacherRepository());

    private final IQuizzRepository quizzRepository = new JdbcQuizzRepository();

    private final IQuestionRepository questionRepository = new JdbcQuestionRepository();

    public static void main(String[] args) {
            Server server = new Server(3001);
            server.startServer();
    }

    /**
     * Starts the server and enters an infinite loop to accept multiple students.
     * <p>
     * The {@link ServerSocket#accept()} method is used to block execution
     * until a client connects.
     * </p>
     * @throws IOException if a network communication error occurs during the socket creation
     * or while accepting a connection.
     */
    public void startServer(){
        System.out.println("Starting server...");
        // Use try-with-resources to ensure the ServerSocket is closed automatically
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started on port " + port);

            while(true) {
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

            // On renvoie la liste à jour pour rafraîchir l'écran du prof.
            List<QuizzDTO> quizzes = quizzRepository.findAll(quizz.teacherUuid());
            reply(clientThreadHandler, new SaveQuizzResponse(true, "OK", quizzes));
        } catch (DatabaseException e) {
            System.err.println("Échec de l'enregistrement du questionnaire : " + e.getMessage());
            reply(clientThreadHandler, new SaveQuizzResponse(false, "Impossible d'enregistrer le questionnaire", List.of()));
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
}