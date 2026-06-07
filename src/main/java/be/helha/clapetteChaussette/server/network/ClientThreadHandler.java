package be.helha.clapetteChaussette.server.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.NextQuestionRequest;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.SubmitAnswerRequest;
import be.helha.clapetteChaussette.common.network.ObjectSocket;

public class ClientThreadHandler implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientThreadHandler.class.getName());

    ObjectSocket objectSocket;
    Logger logger = Logger.getLogger(ClientThreadHandler.class.getName());
    Listener listener;

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
                } else if (event instanceof NextQuestionRequest) {
                    listener.onNextQuestion(this, (NextQuestionRequest) event);
                } else if (event instanceof SubmitAnswerRequest) {
                    listener.onSubmitAnswer(this, (SubmitAnswerRequest) event);
                } else {
                    LOGGER.log(Level.WARNING, "Unsupported network event received: {0}",
                            event == null ? "null" : event.getClass().getName());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Network error in client thread: {0}", e.getMessage());
        } finally {
            try {
                objectSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error closing client socket: {0}", e.getMessage());
            }
            listener.onDisconnect(this);
        }
    }

    public interface Listener {

        void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest);

        void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest);

        /**
         * Called on the server side when a client asks to persist a quizz.
         */
        void onSaveQuizz(ClientThreadHandler clientThreadHandler, SaveQuizzRequest saveQuizzRequest);

        /**
         * Called on the server side when a teacher asks to launch a quizz session.
         */
        void onLaunchQuizz(ClientThreadHandler clientThreadHandler, LaunchQuizzRequest launchQuizzRequest);

        /**
         * Called on the server side when a student asks to join a session.
         */
        void onJoinSession(ClientThreadHandler clientThreadHandler, JoinSessionRequest joinSessionRequest);

        /**
         * Called on the server side when a student leaves a session on purpose.
         */
        void onLeaveSession(ClientThreadHandler clientThreadHandler, LeaveSessionRequest leaveSessionRequest);

        /**
         * Called on the server side when the teacher requests the next question in a
         * live session quiz.
         */
        void onNextQuestion(ClientThreadHandler clientThreadHandler, NextQuestionRequest nextQuestionRequest);

        /**
         * Called on the server side when a student submits their answer to the current
         * question.
         */
        void onSubmitAnswer(ClientThreadHandler clientThreadHandler, SubmitAnswerRequest submitAnswerRequest);

        /**
         * Called once the connection has been closed. No-op by default.
         */
        void onDisconnect(ClientThreadHandler clientThreadHandler);
    }
}
