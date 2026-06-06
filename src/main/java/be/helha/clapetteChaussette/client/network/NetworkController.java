package be.helha.clapetteChaussette.client.network;

import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

import java.io.IOException;
import java.net.Socket;

/**
 * Manages the network connection on the client side for the application.
 * Handles connecting to the server, sending objects, and disconnecting.
 */
public class NetworkController {

    private Socket socket;
    private ObjectSocket objectSocket;
    private Thread listenerThread;
    private boolean isConnected = false;
    private ClientThreadHandler handler;

    /**
     * Creates a new client with the specified server address and port.
     *
     * @param host The IP address or domain name of the server.
     * @param port The port number the server is listening on.
     */
    public NetworkController(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.objectSocket = new ObjectSocket(socket);
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

        this.isConnected = true;
        this.handler = new ClientThreadHandler(objectSocket, listener);
        this.listenerThread = new Thread(handler);
        // Daemon: this background thread must not keep the JVM alive once the JavaFX
        // window is closed (it is otherwise blocked forever on objectSocket.read()).
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
    }

    public void addListener(ClientThreadHandler.Listener listener) throws IOException {
        if (!isConnected) {
            throw new IllegalStateException("Le client n'est pas connecté au serveur.");
        }
        this.handler.addListener(listener);
    }

    public void removeListener(ClientThreadHandler.Listener listener) {
        if (this.handler != null) {
            this.handler.removeListener(listener);
        }
    }

    /**
     * Sends an object to the server through the network stream.
     *
     * @param event The object to send.
     * @throws IOException If the client is not connected or if a network error
     *                     occurs.
     */
    public synchronized void send(NetworkEvent event) throws IOException {
        if (!isConnected || objectSocket == null) {
            throw new IOException("Le client n'est pas connecté au serveur.");
        }
        try {
            this.objectSocket.send(event);
        } catch (IOException e) {
            // it look weird to catch an IOException just to rethrow it, but this way we can
            // add a custom message and preserve the stack trace.
            throw new IOException("Echec d'envoi de l'objet au serveur: " + e.getMessage(), e);
        }
    }

    /**
     * Closes the network connection, shuts down the streams, and stops the
     * listening Thread.
     * If the client is not connected, this method does nothing.
     */
    public synchronized void disconnect() throws IOException {
        if (!isConnected) {
            return;
        }
        this.isConnected = false;

        if (objectSocket != null) {
            objectSocket.close();
        }

        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
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
