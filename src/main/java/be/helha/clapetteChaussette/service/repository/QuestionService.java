package be.helha.clapetteChaussette.service.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.question.Question;

public class QuestionService {

    public QuestionDTO transformQuestionToDTO(Question question) {
        return new QuestionDTO(
                question.getQuuid(),
                question.getQuestionnaireUuid(),
                question.getQuestion(),
                question.getAnswers(),
                question.getType());
    }
}