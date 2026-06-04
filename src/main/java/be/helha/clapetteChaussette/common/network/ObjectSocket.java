package be.helha.clapetteChaussette.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectSocket {
    private static final Logger LOGGER = Logger.getLogger(ObjectSocket.class.getName());
    /**
     * Output stream for sending objects
     */
    private final ObjectOutputStream out;

    /**
     * Input stream for receiving objects
     */
    private final ObjectInputStream in;

    /**
     * Socket used for the connection.
     */
    private final Socket socket;

    /**
     * Creates a new ObjectSocket connected to the specified host and port.
     * This method is for the client only. In the client we couple the socket
     * creation with the ObjectStream creation, so we can directly create an
     * ObjectSocket.
     * 
     * @param host
     * @param port
     * @throws IOException
     */
    public ObjectSocket(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Creates a new ObjectSocket from an existing Socket. This method is for the
     * server only. In the server we first accept a connection and get a Socket,
     * then we create an ObjectSocket from it to handle the object streams.
     * 
     * @param socket
     * @throws IOException
     */
    public ObjectSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) in.readObject();
    }

    public void send(Object object) throws IOException {
        this.out.writeObject(object);
        this.out.flush();
    }

    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }
}
