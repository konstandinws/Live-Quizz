package be.helha.clapetteChaussette.common.models.events;

import java.util.UUID;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;

/**
 * Sent by a student's client to submit their answer to the current question.
 * Carries the question's id so the server can ignore late answers that target a
 * question that is no longer the current one.
 */
public class SubmitAnswerRequest extends NetworkEvent {

    private final UUID questionUuid;
    private final int answerIndex;
    private final String answerText;
    private final QuestionType questionType;

    public SubmitAnswerRequest(UUID questionUuid, int answerIndex, String answerText, QuestionType questionType) {
        this.questionUuid = questionUuid;
        this.answerIndex = answerIndex;
        this.answerText = answerText;
        this.questionType = questionType;
    }

    public UUID getQuestionUuid() {
        return questionUuid;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public String getAnswerText() {
        return answerText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
