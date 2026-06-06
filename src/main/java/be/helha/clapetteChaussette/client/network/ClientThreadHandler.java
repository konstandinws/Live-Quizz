package be.helha.clapetteChaussette.client.network;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.ParticipantNameUpdate;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

public class ClientThreadHandler implements Runnable {

    ObjectSocket objectSocket;
    private static final Logger LOGGER = Logger.getLogger(ClientThreadHandler.class.getName());
    // CopyOnWriteArrayList: listeners are added/removed from the JavaFX thread
    // while the network thread iterates them on each incoming event.
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public ClientThreadHandler(ObjectSocket objectSocket, Listener listener) {
        this.objectSocket = objectSocket;
        this.listeners.add(listener);
    }

    public void run() {
        try {
            while (true) {
                NetworkEvent event = objectSocket.read();
                for (Listener listener : listeners) {
                    dispatchEventToListener(event, listener);
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

    private void dispatchEventToListener(NetworkEvent event, Listener listener) {
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
        } else if (event instanceof ParticipantNameUpdate) {
            listener.onParticipantNameUpdate((ParticipantNameUpdate) event);
        } else {
            LOGGER.log(Level.WARNING, "Unsupported network event received: {0}",
                    event == null ? "null" : event.getClass().getName());
        }
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
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
         * Called on the client side when the participant names of the session change.
         * 
         * @param participantNameUpdate
         */
        default void onParticipantNameUpdate(ParticipantNameUpdate participantNameUpdate) {
        }

        /**
         * Called when a network error breaks the connection.
         */
        default void onError(Exception e) {
        }

    }
}
