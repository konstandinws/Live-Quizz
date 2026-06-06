package be.helha.clapetteChaussette.server.repository;

import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Question entities and their associated
 * Answers.
 */
public interface IQuestionRepository {
    /**
     * Saves a new question and its associated possible answers into the database.
     * Both operations (question and answers) are managed together.
     *
     * @param question The {@link QuestionDTO} object containing the question
     *                 details and answers.
     * @throws DatabaseException if a database access error occurs during insertion.
     */
    void save(QuestionDTO question) throws DatabaseException;

    /**
     * Saves multiple questions and their associated answers into the database.
     * 
     * @param questions The iterable of {@link QuestionDTO} objects containing the
     *                  questions.
     * @throws DatabaseException if a database access error occurs during insertion.
     */
    void saveQuestions(Iterable<QuestionDTO> questions) throws DatabaseException;

    /**
     * Retrieves a question and all its associated possible answers from the
     * database.
     *
     * @param questionUuid The unique identifier of the question to retrieve.
     * @return An {@link Optional} containing the {@link QuestionDTO} if found,
     *         or an empty {@link Optional} if no question matches the UUID.
     * @throws DatabaseException if a database access error occurs during retrieval.
     */
    Optional<QuestionDTO> findByUuid(UUID questionUuid) throws DatabaseException;

    /**
     * Retrieves all questions of a quizz (with their answers), in insertion order.
     *
     * @param quizzUuid the quizz whose questions are requested.
     * @return the list of {@link QuestionDTO}, empty if the quizz has none.
     * @throws DatabaseException if a database access error occurs during retrieval.
     */
    List<QuestionDTO> findByQuizz(UUID quizzUuid) throws DatabaseException;
}
