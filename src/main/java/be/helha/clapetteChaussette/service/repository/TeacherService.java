package be.helha.clapetteChaussette.service.repository;

import be.helha.clapetteChaussette.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.model.Teacher;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.ITeacherRepository;
import be.helha.clapetteChaussette.utils.PasswordHasher;

import java.util.Optional;
import java.util.UUID;

/**
 * Service layer responsible for orchestrating teacher-related business logic.
 * <p>
 * This service acts as the bridge between the controllers (UI) and the
 * repository,
 * ensuring all business rules, validations, and security measures (like
 * hashing)
 * are applied before data persistence.
 * </p>
 */
public class TeacherService {

    private final ITeacherRepository repository;
    private final int MINIMUM_PASSWORD_LENGTH = 6;

    /**
     * Constructs a new TeacherService.
     *
     * @param repository The repository used to access and persist teacher data.
     */
    public TeacherService(ITeacherRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new teacher in the system.
     * <p>
     * Validates the input data, ensures the username is unique, securely hashes
     * the password, and persists the new teacher record to the database.
     * </p>
     *
     * @param name     The desired username for the new teacher.
     * @param password The plain-text password (must be at least
     *                 MINIMUM_PASSWORD_LENGTH characters).
     * @return The newly created {@link Teacher} domain model.
     * @throws IllegalArgumentException      if the name is blank or the password is
     *                                       too short.
     * @throws TeacherAlreadyExistsException if a teacher with the given name
     *                                       already exists.
     */
    public Teacher registerNewTeacher(String name, String password) {

        if (name.isBlank()) {
            throw new IllegalArgumentException("invalid teacher name");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("password not strong enough");
        }
        if (repository.findByName(name).isPresent()) {
            throw new TeacherAlreadyExistsException(name);
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getUuid(), name, hashedPassword);

        repository.save(teacherDTO);
        return teacher;
    }

    /**
     * Authenticates a teacher based on their name and plain-text password.
     *
     * @param name     The username of the teacher.
     * @param password The plain-text password provided during login.
     * @return The authenticated {@link Teacher} domain model reconstructed from the
     *         database.
     * @throws LoginFailedException if no matching record is found or the password
     *                              is incorrect.
     */
    public Teacher teacherLogin(String name, String password) {

        Optional<TeacherDTO> dbResponse = repository.findByName(name);

        // we verify that the teacher exists
        if (dbResponse.isPresent()) {
            TeacherDTO teacherDTO = dbResponse.get();
            // we verify if the password is correct
            if (PasswordHasher.verifyPassword(password, teacherDTO.hashedPassword())) {
                return createTeacherFromDB(teacherDTO);
            }
        }
        throw new LoginFailedException();
    }

    private Teacher createTeacherFromDB(TeacherDTO teacherDTO) {
        UUID uuid = teacherDTO.uuid();
        String name = teacherDTO.name();

        return new Teacher(uuid, name);
    }

}
