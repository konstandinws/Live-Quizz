package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.utils.PasswordHasher;

import java.util.UUID;

public class TeacherService {

    TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public void registerNewTeacher(String name, String password) throws Exception {
        if (repository.findByName(name).isPresent()) {
            throw new Exception("This teacher already exist");
        }

        if (name.isBlank()){
            throw new Exception("invalid teacher name");
        }

        if(password.length() < 6) {
            throw new Exception("password not strong enough");
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        TeacherDTO teacher = new TeacherDTO(UUID.randomUUID(), name, hashedPassword);

        repository.save(teacher);
    }
}
