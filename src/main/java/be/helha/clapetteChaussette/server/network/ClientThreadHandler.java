package be.helha.clapetteChaussette.server.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

public class ClientThreadHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientThreadHandler.class.getName());

    private final Listener listener;

    ObjectSocket objectSocket;

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

    public void run() {
        try {
            while (true) {
                // wait for a message from the other side of the connection
                NetworkEvent event = this.objectSocket.read();
                if (event instanceof LoginRequest) {
                    listener.onLogin(this, (LoginRequest) event);
                } else if (event instanceof RegisterRequest) {
                    listener.onRegister(this, (RegisterRequest) event);
                } else if (event instanceof SaveQuizzRequest) {
                    listener.onSaveQuizz(this, (SaveQuizzRequest) event);
                } else if (event instanceof LaunchQuizzRequest) {
                    listener.onLaunchQuizz(this, (LaunchQuizzRequest) event);
                } else if (event instanceof JoinSessionRequest) {
                    listener.onJoinSession(this, (JoinSessionRequest) event);
                } else if (event instanceof LeaveSessionRequest) {
                    listener.onLeaveSession(this, (LeaveSessionRequest) event);
                } else if (event instanceof LoginResponse) {
                    listener.onLoginResponse((LoginResponse) event);
                } else if (event instanceof SaveQuizzResponse) {
                    listener.onSaveQuizzResponse((SaveQuizzResponse) event);
                } else if (event instanceof LaunchQuizzResponse) {
                    listener.onLaunchQuizzResponse((LaunchQuizzResponse) event);
                } else if (event instanceof JoinSessionResponse) {
                    listener.onJoinSessionResponse((JoinSessionResponse) event);
                } else if (event instanceof ParticipantCountUpdate) {
                    listener.onParticipantCountUpdate((ParticipantCountUpdate) event);
                } else {
                    LOGGER.log(Level.WARNING, "Unsupported network event received: {0}",
                            event == null ? "null" : event.getClass().getName());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // error during reading from the socket (e.g., client disconnected, network
            // error, invalid data)
            listener.onError(e);
        } finally {
            try {
                objectSocket.close();
            } catch (IOException e) {
                // error while closing the socket
                listener.onError(e);
            }
            listener.onDisconnect(this);
        }
    }

    public interface Listener {

        void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest);

        void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest);

        /**
         * Called on the server side when a client asks to persist a quizz.
         * No-op by default so the client side does not need to implement it.
         */
        default void onSaveQuizz(ClientThreadHandler clientThreadHandler, SaveQuizzRequest saveQuizzRequest) {
        }

        /**
         * Called on the server side when a teacher asks to launch a quizz session.
         * No-op by default so the client side does not need to implement it.
         */
        default void onLaunchQuizz(ClientThreadHandler clientThreadHandler, LaunchQuizzRequest launchQuizzRequest) {
        }

        /**
         * Called on the server side when a student asks to join a session.
         * No-op by default so the client side does not need to implement it.
         */
        default void onJoinSession(ClientThreadHandler clientThreadHandler, JoinSessionRequest joinSessionRequest) {
        }

        /**
         * Called on the server side when a student leaves a session on purpose.
         * No-op by default so the client side does not need to implement it.
         */
        default void onLeaveSession(ClientThreadHandler clientThreadHandler, LeaveSessionRequest leaveSessionRequest) {
        }

        /**
         * Called on the client side when the server replies to a login request.
         * No-op by default so the server side does not need to implement it.
         */
        default void onLoginResponse(LoginResponse loginResponse) {
        }

        /**
         * Called on the client side when the server replies to a save-quizz request.
         * No-op by default so the server side does not need to implement it.
         */
        default void onSaveQuizzResponse(SaveQuizzResponse saveQuizzResponse) {
        }

        /**
         * Called on the client side when the server replies to a launch-quizz request.
         * No-op by default so the server side does not need to implement it.
         */
        default void onLaunchQuizzResponse(LaunchQuizzResponse launchQuizzResponse) {
        }

        /**
         * Called on the client side when the server replies to a join-session request.
         * No-op by default so the server side does not need to implement it.
         */
        default void onJoinSessionResponse(JoinSessionResponse joinSessionResponse) {
        }

        /**
         * Called on the client side when the participant count of the session changes.
         * No-op by default so the server side does not need to implement it.
         */
        default void onParticipantCountUpdate(ParticipantCountUpdate participantCountUpdate) {
        }

        /**
         * Called when a network error breaks the connection. No-op by default.
         */
        default void onError(Exception e) {
        }

        /**
         * Called once the connection has been closed. No-op by default.
         */
        default void onDisconnect(ClientThreadHandler clientThreadHandler) {
        }
    }
}
