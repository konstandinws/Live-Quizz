package be.helha.clapetteChaussette.network;

import be.helha.clapetteChaussette.common.models.LoginRequest;
import be.helha.clapetteChaussette.common.models.RegisterRequest;
import be.helha.clapetteChaussette.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.model.Teacher;
import be.helha.clapetteChaussette.repository.db.JdbcTeacherRepository;
import be.helha.clapetteChaussette.service.repository.TeacherService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the server for the Clapette Chaussette application.
 * <p>
 * This class is responsible for initializing the {@link ServerSocket}
 * and listening for incoming student connections.
 * </p>
 */
public class Server implements ClientThreadHandler.Listener{

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
        } catch (LoginFailedException e) {
            System.out.println("Échec de connexion pour " + name);
        }
    }

    @Override
    public void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest) {
        String name = registerRequest.getTeacher().getName();
        try {
            // Le serveur hashe le mot de passe (BCrypt) avant de le persister.
            Teacher teacher = teacherService.registerNewTeacher(name, registerRequest.getPassword());
            System.out.println("Nouveau prof créé : " + teacher.getName());
        } catch (TeacherAlreadyExistsException e) {
            System.out.println("Le prof existe déjà : " + name);
        } catch (IllegalArgumentException e) {
            System.out.println("Données d'inscription invalides : " + e.getMessage());
        }
    }
}