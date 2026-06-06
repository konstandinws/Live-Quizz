package be.helha.clapetteChaussette.common.models.events;

import java.util.UUID;

/**
 * Sent by a student's client to submit their answer to the current question.
 * Carries the question's id so the server can ignore late answers that target a
 * question that is no longer the current one.
 */
public class SubmitAnswerRequest extends NetworkEvent {

    private final UUID questionUuid;
    private final int answerIndex;

    public SubmitAnswerRequest(UUID questionUuid, int answerIndex) {
        this.questionUuid = questionUuid;
        this.answerIndex = answerIndex;
    }

    public UUID getQuestionUuid() {
        return questionUuid;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }
}
