package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.common.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.common.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.server.repository.TeacherService;

/**
 * Service responsible for authentication operations.
 * Handles teacher login and registration.
 */
public class AuthService {

    private final TeacherService teacherService;

    public AuthService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * Authenticates a teacher with their name and password.
     *
     * @param name     the teacher's name
     * @param password the teacher's password
     * @return the authenticated Teacher object
     * @throws LoginFailedException if authentication fails
     * @throws DatabaseException    if a database error occurs
     */
    public Teacher login(String name, String password) throws LoginFailedException, DatabaseException {
        return teacherService.teacherLogin(name, password);
    }

    /**
     * Registers a new teacher with name and password.
     *
     * @param name     the teacher's name
     * @param password the teacher's password
     * @return the newly created Teacher object
     * @throws TeacherAlreadyExistsException if the teacher already exists
     * @throws IllegalArgumentException      if the data is invalid
     * @throws DatabaseException             if a database error occurs
     */
    public Teacher register(String name, String password)
            throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        return teacherService.registerNewTeacher(name, password);
    }
}
