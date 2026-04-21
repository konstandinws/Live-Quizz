package be.helha.clapetteChaussette.repository;


import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.dto.TeacherResponseDTO;

import java.util.Optional;

/**
 * Repository interface for managing Teacher entities.
 * <p>
 * Defines the standard operations for teacher persistence, agnostic of the
 * underlying database technology.
 * </p>
 */
public interface TeacherRepository {

   /**
    * Saves a new teacher record into the system.
    *
    * @param teacher The {@link TeacherDTO} object containing the teacher's unique identifier,
    * name, and hashed password.
    * @throws DatabaseException if a persistence error occurs.
    */
   void save(TeacherDTO teacher);

   /**
    * Retrieves a teacher based on the provided credentials.
    * <p>
    * Returns a {@link TeacherResponseDTO} which safely excludes sensitive data
    * such as the password hash.
    * </p>
    *
    * @param cred The {@link Credentials} object containing the teacher's name and hashed password to match.
    * @return An {@link Optional} containing the {@link TeacherResponseDTO} if the credentials match an existing record,
    * or an empty {@link Optional} if no match is found.
    * @throws DatabaseException if a data retrieval error occurs.
    */
   Optional<TeacherResponseDTO> find(Credentials cred);

   Optional<Boolean> findByName(String name);
}
