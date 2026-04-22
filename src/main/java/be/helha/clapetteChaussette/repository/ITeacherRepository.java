package be.helha.clapetteChaussette.repository;


import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;

import java.util.Optional;

/**
 * Repository interface for managing Teacher entities.
 * <p>
 * Defines the standard operations for teacher persistence, agnostic of the
 * underlying database technology.
 * </p>
 */
public interface ITeacherRepository {

   /**
    * Saves a new teacher record into the system.
    *
    * @param teacher The {@link TeacherDTO} object containing the teacher's unique identifier,
    * name, and hashed password.
    * @throws DatabaseException if a persistence error occurs.
    */
   void save(TeacherDTO teacher);

   /**
    * Retrieves a teacher by their name, including their stored password hash.
    * <p>
    * This method is used both during registration to check for username availability,
    * and during authentication to retrieve the stored credentials for verification.
    * </p>
    *
    * @param name The username of the teacher to search for.
    * @return An {@link Optional} containing the {@link TeacherDTO} if a match is found,
    * or an empty {@link Optional} if the name does not exist in the database.
    */
   Optional<TeacherDTO> findByName(String name);
}
