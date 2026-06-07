package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;

import java.util.List;
import java.util.UUID;

/**
 * Service responsible for quizz management operations.
 * Handles saving and retrieving quizzes and their questions.
 */
public class QuizzService {

    private final IQuizzRepository quizzRepository;
    private final IQuestionRepository questionRepository;

    public QuizzService(IQuizzRepository quizzRepository, IQuestionRepository questionRepository) {
        this.quizzRepository = quizzRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Saves a quizz and its associated questions.
     *
     * @param quizz     the quizz to save
     * @param questions the questions for this quizz
     * @throws DatabaseException if a database error occurs
     */
    public void saveQuizz(QuizzDTO quizz, List<QuestionDTO> questions) throws DatabaseException {
        quizzRepository.save(quizz);
        questionRepository.saveQuestions(questions);
    }

    /**
     * Retrieves all quizzes for a given teacher.
     *
     * @param teacherUuid the UUID of the teacher
     * @return a list of all quizzes for that teacher
     * @throws DatabaseException if a database error occurs
     */
    public List<QuizzDTO> getTeacherQuizzes(UUID teacherUuid) throws DatabaseException {
        return quizzRepository.findAll(teacherUuid);
    }

    /**
     * Retrieves all questions for a given quizz.
     *
     * @param quizzUuid the UUID of the quizz
     * @return a list of all questions for that quizz
     * @throws DatabaseException if a database error occurs
     */
    public List<QuestionDTO> getQuizzQuestions(UUID quizzUuid) throws DatabaseException {
        return questionRepository.findByQuizz(quizzUuid);
    }
}
