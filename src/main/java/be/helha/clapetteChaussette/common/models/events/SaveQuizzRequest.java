package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;

import java.util.List;

/**
 * Sent by the client to ask the server to persist a quizz and its questions.
 * The {@link QuizzDTO} already carries the owning teacher's UUID.
 */
public class SaveQuizzRequest extends NetworkEvent {

    private final QuizzDTO quizz;
    private final List<QuestionDTO> questions;

    public SaveQuizzRequest(QuizzDTO quizz, List<QuestionDTO> questions) {
        this.quizz = quizz;
        this.questions = questions;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }
}
