package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;

public class NextQuestionResponse extends NetworkEvent {

    private boolean isSuccess;
    private String errorMessage;

    private final QuestionDTO questionDTO;
    private final int nextQuestionIndex;

    public NextQuestionResponse(boolean isSuccess, String errorMessage, QuestionDTO questionDTO,
            int nextQuestionIndex) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;

        this.questionDTO = questionDTO;
        this.nextQuestionIndex = nextQuestionIndex;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public QuestionDTO getQuestion() {
        return questionDTO;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getNextQuestionIndex() {
        return nextQuestionIndex;
    }
}
