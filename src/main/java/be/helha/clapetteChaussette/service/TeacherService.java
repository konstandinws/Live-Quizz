package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.exception.LoginFailedException;
import be.helha.clapetteChaussette.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.model.Teacher;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.dto.TeacherResponseDTO;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.utils.PasswordHasher;

import java.util.Optional;
import java.util.UUID;

public class TeacherService {

    TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    // saves the newly created teacher int DataBase and creates a new Teacher to Store it in the current session
    public Teacher registerNewTeacher(String name, String password) throws RuntimeException {

        if (name.isBlank()){
            throw new IllegalArgumentException("invalid teacher name");
        }
        if(password.length() < 6) {
            throw new IllegalArgumentException("password not strong enough");
        }
        if (repository.findByName(name).isPresent()) {
            throw new TeacherAlreadyExistsException("This teacher already exist");
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getUuid(), name, hashedPassword);

        repository.save(teacherDTO);
        return teacher;
    }

    // get the teacher from the DataBase and return a new object Teacher
    public Teacher teacherLogin(Credentials credentials) throws RuntimeException {
        Optional<TeacherResponseDTO> dbResponse = repository.find(credentials);

        if(dbResponse.isPresent()) {
            TeacherResponseDTO teacherResponseDTO = dbResponse.get();
            return createTeacherFromDB(teacherResponseDTO);
        }
        else {
            throw new LoginFailedException();
        }
    }

    private Teacher createTeacherFromDB(TeacherResponseDTO teacherResponseDTO) {
        UUID uuid = teacherResponseDTO.uuid();
        String name = teacherResponseDTO.name();

        return new Teacher(uuid, name);
    }
}
