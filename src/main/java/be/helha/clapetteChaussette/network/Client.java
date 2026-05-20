package be.helha.clapetteChaussette.network;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private ObjectSocket objectSocket;
    private Thread networkThread;
    private boolean isConnected = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public synchronized void connect(ThreadHandler.Listener listener) {
        if (isConnected) {
            return;
        }
        try {
            this.socket = new Socket(host, port);
            this.objectSocket = new ObjectSocket(this.socket);
            this.isConnected = true;

            ThreadHandler threadHandler = new ThreadHandler(this.objectSocket, listener);

            this.networkThread = new Thread(threadHandler, "Wooclap-Client-Network-Thread");
            this.networkThread.start();
        } catch (IOException e) {
            this.isConnected = false;
            if (listener != null) {
                listener.onError(e);
            }
        }
    }

    public synchronized void send(Object object) throws IOException {
    if (!isConnected || objectSocket == null) {
        throw new IOException("Client not connected");
    }
    this.objectSocket.send(object);
    }
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
    public boolean isConnected() {
        return isConnected;
    }
}



