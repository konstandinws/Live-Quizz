package be.helha.clapetteChaussette.network;

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
    public ObjectSocket(Socket socket) throws IOException {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }
    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) in.readObject();
    }
    public void close() {
        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error closing ObjectSocket streams", e);
        }
    }
}
