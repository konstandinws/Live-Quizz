package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;

import java.util.Optional;
import java.util.UUID;

/**
 * Service responsible for answer submission operations.
 * Handles recording and processing student answers.
 */
public class AnswerService {

    /**
     * Records an answer for a multiple-choice or poll question.
     *
     * @param session       the session
     * @param clientHandler the student's client handler
     * @param questionUuid  the UUID of the question being answered
     * @param answerIndex   the index of the chosen answer
     * @return true if the answer was recorded (valid for current question)
     */
    public boolean recordMcqOrPollAnswer(Session session, ClientThreadHandler clientHandler,
            UUID questionUuid, int answerIndex) {
        Optional<QuestionDTO> current = session.getCurrentQuestion();
        if (current.isEmpty() || !current.get().uuid().equals(questionUuid)) {
            return false;
        }

        session.recordAnswer(clientHandler, answerIndex);
        return true;
    }

    /**
     * Records an answer for an open-ended question.
     *
     * @param session       the session
     * @param clientHandler the student's client handler
     * @param questionUuid  the UUID of the question being answered
     * @param answerText    the text answer provided by the student
     * @return true if the answer was recorded (valid for current question)
     */
    public boolean recordOpenAnswer(Session session, ClientThreadHandler clientHandler,
            UUID questionUuid, String answerText) {
        Optional<QuestionDTO> current = session.getCurrentQuestion();
        if (current.isEmpty() || !current.get().uuid().equals(questionUuid)) {
            return false;
        }

        session.recordTextAnswer(clientHandler, answerText);
        return true;
    }

    /**
     * Records an answer based on the question type.
     *
     * @param session       the session
     * @param clientHandler the student's client handler
     * @param questionUuid  the UUID of the question being answered
     * @param questionType  the type of question
     * @param answerIndex   the index of the chosen answer (for MCQ/POLL)
     * @param answerText    the text answer (for OPEN)
     * @return true if the answer was recorded
     */
    public boolean recordAnswer(Session session, ClientThreadHandler clientHandler, UUID questionUuid,
            QuestionType questionType, int answerIndex, String answerText) {
        return switch (questionType) {
            case MCQ, POLL -> recordMcqOrPollAnswer(session, clientHandler, questionUuid, answerIndex);
            case OPEN -> recordOpenAnswer(session, clientHandler, questionUuid, answerText);
        };
    }
}
