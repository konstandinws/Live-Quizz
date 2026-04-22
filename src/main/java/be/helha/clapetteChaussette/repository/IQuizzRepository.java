package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Quiz entities.
 * <p>
 * Defines the standard operations for saving and retrieving quizzes,
 * agnostic of the underlying database technology.
 * </p>
 */
public interface IQuizzRepository {

    /**
     * Saves a new quiz into the system.
     *
     * @param quizz The {@link QuizzDTO} object containing the quiz details
     * (its unique identifier, the owner teacher's identifier, and the quiz name).
     * @throws DatabaseException if a database access error occurs during insertion.
     */
    void save(QuizzDTO quizz);

    /**
     * Retrieves all quizzes associated with a specific teacher.
     *
     * @param teacherUuid The unique identifier of the teacher whose quizzes are to be retrieved.
     * @return A {@link List} of {@link QuizzDTO} objects representing the teacher's quizzes.
     * Returns an empty list if no quizzes are found for the given teacher.
     * @throws DatabaseException if a database access error occurs during retrieval.
     */
    List<QuizzDTO> findAll(UUID teacherUuid);
}
