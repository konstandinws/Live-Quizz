package be.helha.clapetteChaussette.server.network;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

public class ClientThreadHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientThreadHandler.class.getName());

    private final Listener listener;

    ObjectSocket objectSocket ;

    public ClientThreadHandler(ObjectSocket objectSocket, Listener listener) {
        this.objectSocket = objectSocket;
        this.listener = listener;
    }

    /**
     * Sends an object back through this handler's socket.
     * Used by the server to reply to the client on its own connection.
     *
     * @param object the object to send.
     * @throws IOException if a network error occurs.
     */
    public void send(Object object) throws IOException {
        this.objectSocket.send(object);
    }

    public void run(){
        try {
            while (true) {
                    // wait for a message from the other side of the connection
                    NetworkEvent event = this.objectSocket.read();
                    if (event instanceof LoginRequest) {
                        listener.onLogin(this, (LoginRequest) event);
                    } else if (event instanceof RegisterRequest) {
                        listener.onRegister(this, (RegisterRequest) event);
                    } else if (event instanceof LoginResponse) {
                        listener.onLoginResponse((LoginResponse) event);
                    } else {
                        LOGGER.log(Level.WARNING, "Unsupported network event received: {0}",
                                event == null ? "null" : event.getClass().getName());
                    }
            }
        } catch (IOException | ClassNotFoundException e) {
            listener.onError(e);
        } finally {
            objectSocket.close();
            listener.onDisconnect();
        }
    }

    public interface  Listener {

        void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest);
        void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest);

        /**
         * Called on the client side when the server replies to a login request.
         * No-op by default so the server side does not need to implement it.
         */
        default void onLoginResponse(LoginResponse loginResponse) {}

        /**
         * Called when a network error breaks the connection. No-op by default.
         */
        default void onError(Exception e) {}

        /**
         * Called once the connection has been closed. No-op by default.
         */
        default void onDisconnect() {}
    }
}
