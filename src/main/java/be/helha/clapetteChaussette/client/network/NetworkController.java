package be.helha.clapetteChaussette.client.network;

import be.helha.clapetteChaussette.common.network.ObjectSocket;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;

import java.io.IOException;
import java.net.Socket;
/**
 * Manages the network connection on the client side for the application.
 * Handles connecting to the server, sending objects, and disconnecting.
 */
public class NetworkController {

    private final String host;
    private final int port;

    private Socket socket;
    private ObjectSocket objectSocket;
    private Thread networkThread;
    private boolean isConnected = false;
    /**
     * Creates a new client with the specified server address and port.
     *
     * @param host The IP address or domain name of the server.
     * @param port The port number the server is listening on.
     */
    public NetworkController(String host, int port) {
        this.host = host;
        this.port = port;
    }
    /**
     * Establishes a connection with the server and starts a background Thread
     * to listen for incoming messages.
     * If the client is already connected, this method does nothing.
     *
     * @param listener The listener that handles received messages and errors.
     */
    public synchronized void connect(ClientThreadHandler.Listener listener) {
        if (isConnected) {
            return;
        }
        try {
            this.socket = new Socket(host, port);
            this.objectSocket = new ObjectSocket(this.socket);
            this.isConnected = true;

            ClientThreadHandler threadHandler = new ClientThreadHandler(this.objectSocket, listener);

            this.networkThread = new Thread(threadHandler, "clapetteChaussette-Client-Network");
            this.networkThread.start();
        } catch (IOException e) {
            this.isConnected = false;
            if (listener != null) {
                listener.onError(e);
            }
        }
    }
    /**
     * Sends an object to the server through the network stream.
     *
     * @param object The object to send.
     * @throws IOException If the client is not connected or if a network error occurs.
     */
    public synchronized void send(Object object) throws IOException {
    if (!isConnected || objectSocket == null) {
        throw new IOException("Client not connected");
    }
    this.objectSocket.send(object);
    }
    /**
     * Closes the network connection, shuts down the streams, and stops the listening Thread.
     * If the client is not connected, this method does nothing.
     */
    public synchronized void disconnect() {
        if (!isConnected) {
            return;
        }
        this.isConnected = false;

        if (objectSocket != null) {
            objectSocket.close();
        }

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {

        }
        if (networkThread != null && networkThread.isAlive()) {
            networkThread.interrupt();
        }
    }
    /**
     * Checks whether the client is currently connected to the server.
     *
     * @return true if the client is connected, false otherwise.
     */
    public boolean isConnected() {
        return isConnected;
    }
}



