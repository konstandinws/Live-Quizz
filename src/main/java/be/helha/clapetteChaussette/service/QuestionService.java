package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.JdbcIQuizzRepository;

import java.util.List;
import java.util.UUID;

public class QuestionService {
    private final JdbcIQuizzRepository quizzRepository;

    public QuestionService(JdbcIQuizzRepository quizzRepository) {
        this.quizzRepository = quizzRepository;
    }

    public List<QuizzDTO> getQuizzesByTeacher(UUID teacherUuid) {
        return quizzRepository.findAll(teacherUuid);
    }

    public QuestionDTO transformQuestionToDTO(Question question) {
        return new QuestionDTO(
                question.getQuuid(),
                question.getQuestionnaireUuid(),
                question.getQuestion(),
                question.getAnswers(),
                question.getType());
    }
}