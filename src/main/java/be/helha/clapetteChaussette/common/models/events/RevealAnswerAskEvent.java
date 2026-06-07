package be.helha.clapetteChaussette.common.models.events;

import java.util.UUID;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;

/**
 * Sent by the teacher's client to the server when they ask to reveal the
 * correct answer. The server have the hash map of question uuid to correct
 * answer, so it can check if the question is still the current one and reply
 * with the correct answer if it is, or ignore.
 */

public class RevealAnswerAskEvent extends NetworkEvent {
    private final UUID questionUuid;
    private final QuestionType questionType;

    public RevealAnswerAskEvent(UUID questionUuid, QuestionType questionType) {
        this.questionUuid = questionUuid;
        this.questionType = questionType;
    }

    public UUID getQuestionUuid() {
        return questionUuid;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
