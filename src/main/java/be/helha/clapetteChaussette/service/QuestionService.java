package be.helha.clapetteChaussette.service;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.repository.QuizzRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class QuestionService {
    private final QuizzRepository quizzRepository;

    public QuestionService(QuizzRepository quizzRepository) {
        this.quizzRepository = quizzRepository;
    }

    public List<QuizzDTO> getQuizzesByTeacher(UUID teacherUuid) {
        return quizzRepository.findAll(teacherUuid).orElse(Collections.emptyList());

    public QuestionDTO transformQuestionToDTO(Question question) {
        return new QuestionDTO(
                question.getQuuid(),
                question.getQuestionnaireUuid(),
                question.getQuestion(),
                question.getAnswers(),
                question.getType()
        );
    }
}
