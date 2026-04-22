package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Question entities and their associated Answers.
 */
public interface IQuestionRepository {
    /**
     * Saves a new question and its associated possible answers into the database.
     * Both operations (question and answers) are managed together.
     *
     * @param question The {@link QuestionDTO} object containing the question details and answers.
     * @throws DatabaseException if a database access error occurs during insertion.
     */
    void save(QuestionDTO question);

    /**
     * Retrieves a question and all its associated possible answers from the database.
     *
     * @param questionUuid The unique identifier of the question to retrieve.
     * @return An {@link Optional} containing the {@link QuestionDTO} if found,
     * or an empty {@link Optional} if no question matches the UUID.
     * @throws DatabaseException if a database access error occurs during retrieval.
     */
    Optional<QuestionDTO> findByUuid(UUID questionUuid);
}
