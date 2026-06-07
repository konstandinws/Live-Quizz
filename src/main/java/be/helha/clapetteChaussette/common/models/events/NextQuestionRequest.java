package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;

/**
 * Event sent by the teacher client to the server to request the next question
 * in the live session quiz. The server will respond with a NextQuestionResponse
 * containing the next question and its possible answers.
 */
public class NextQuestionRequest extends NetworkEvent {

    private final QuizzDTO quizz;
    private final int questionIndex;

    public NextQuestionRequest(QuizzDTO quizz, int questionIndex) {
        this.quizz = quizz;
        this.questionIndex = questionIndex;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

}
