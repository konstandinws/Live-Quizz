package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.questions.Answer;

import java.util.List;

/**
 * Service responsible for question-related operations.
 * Handles manipulating questions, such as hiding correct answers.
 */
public class QuestionService {

    /**
     * Returns a copy of the question whose answers no longer reveal the correct
     * ones.
     * This is used to send questions to students without revealing the answers.
     *
     * @param question the original question
     * @return a copy of the question with correct answers hidden
     */
    public QuestionDTO hideCorrectAnswers(QuestionDTO question) {
        List<Answer> hidden = question.answers().stream()
                .map(answer -> new Answer(answer.getAnswer(), false))
                .toList();
        return new QuestionDTO(question.uuid(), question.quizzUuid(), question.title(), hidden, question.type());
    }
}
