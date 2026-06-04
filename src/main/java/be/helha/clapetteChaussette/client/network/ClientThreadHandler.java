package be.helha.clapetteChaussette.client.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

public class ClientThreadHandler implements Runnable {

    ObjectSocket objectSocket;
    private static final Logger LOGGER = Logger.getLogger(ClientThreadHandler.class.getName());
    Listener listener;

    public ClientThreadHandler(ObjectSocket objectSocket, Listener listener) {
        this.objectSocket = objectSocket;
        this.listener = listener;
    }

    public void run() {
        try {
            while (true) {
                NetworkEvent event = objectSocket.read();
                if (event instanceof LoginResponse) {
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
            LOGGER.severe("Error in client thread: " + e.getMessage());
        } finally {
            try {
                objectSocket.close();
            } catch (IOException e) {
                LOGGER.severe("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public interface Listener {
        /**
         * Called on the client side when the server replies to a login request.
         */
        default void onLoginResponse(LoginResponse loginResponse) {
        }

        /**
         * Called on the client side when the server replies to a save-quizz request.
         */
        default void onSaveQuizzResponse(SaveQuizzResponse saveQuizzResponse) {
        }

        /**
         * Called on the client side when the server replies to a launch-quizz request.
         */
        default void onLaunchQuizzResponse(LaunchQuizzResponse launchQuizzResponse) {
        }

        /**
         * Called on the client side when the server replies to a join-session request.
         */
        default void onJoinSessionResponse(JoinSessionResponse joinSessionResponse) {
        }

        /**
         * Called on the client side when the participant count of the session changes.
         */
        default void onParticipantCountUpdate(ParticipantCountUpdate participantCountUpdate) {
        }

        /**
         * Called when a network error breaks the connection. No-op by default.
         */
        void onError(Exception e);

    }
}
