package be.helha.clapetteChaussette.network;

import java.net.*;
import java.io.*;

/**
 * Represents the server for the Clapette Chaussette application.
 * <p>
 * This class is responsible for initializing the {@link ServerSocket}
 * and listening for incoming student connections.
 * </p>
 */
public class Server {

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
        // Use try-with-resources to ensure the ServerSocket is closed automatically
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started on port " + port);

            while(true) {
                // Wait for a student to connect (blocking method)
                Socket client = serverSocket.accept();
                System.out.println("New student connected: " + client.getInetAddress());

                // TODO : Passer ce client au ThreadHandler pour ne pas bloquer les suivants            }
            }
        } catch (IOException e) {
            System.err.println("Server network error: " + e.getMessage());
        }
    }
}