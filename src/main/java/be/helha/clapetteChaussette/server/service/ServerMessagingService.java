package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.ParticipantNameUpdate;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;

import java.io.IOException;

/**
 * Service responsible for server-side messaging and broadcasting.
 * Handles sending responses and broadcasting updates to clients.
 */
public class ServerMessagingService {

    private final QuestionService questionService;

    public ServerMessagingService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Sends a generic message to a client handler, logging any network errors.
     *
     * @param clientThreadHandler the client to send to
     * @param event               the event/response to send
     */
    public void send(ClientThreadHandler clientThreadHandler, Object event) {
        try {
            clientThreadHandler.send(event);
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer un message au client : " + e.getMessage());
        }
    }

    /**
     * Notifies the teacher (host) and every student of the session's participant
     * count and names.
     *
     * @param session the session to broadcast to
     */
    public void broadcastParticipantUpdate(Session session) {
        ParticipantCountUpdate updateNumber = new ParticipantCountUpdate(session.getNumberParticipants());
        ParticipantNameUpdate updateNames = new ParticipantNameUpdate(session.getParticipantPseudos());
        send(session.getHost(), updateNumber);
        send(session.getHost(), updateNames);
        for (ClientThreadHandler client : session.getClients()) {
            send(client, updateNumber);
        }
    }

    /**
     * Broadcasts the next question to all participants in a session.
     * Teachers receive the full question with correct answers,
     * students receive a version with correct answers hidden.
     *
     * @param session  the session
     * @param question the question to broadcast
     * @param index    the question index
     */
    public void broadcastNextQuestion(Session session, QuestionDTO question, int index) {
        // The teacher (host) gets the full question, correct answers included.
        send(session.getHost(), new NextQuestionResponse(true, "OK", question, index));

        // Students get a copy without the correct answers (anti-cheat).
        QuestionDTO hiddenQuestion = questionService.hideCorrectAnswers(question);
        NextQuestionResponse studentResponse = new NextQuestionResponse(true, "OK", hiddenQuestion, index);
        for (ClientThreadHandler client : session.getClients()) {
            send(client, studentResponse);
        }
    }
}
