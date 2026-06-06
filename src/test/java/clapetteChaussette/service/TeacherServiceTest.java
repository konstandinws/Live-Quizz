package clapetteChaussette.service;

import be.helha.clapetteChaussette.common.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.common.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.server.repository.ITeacherRepository;
import be.helha.clapetteChaussette.client.service.repository.TeacherService;
import be.helha.clapetteChaussette.server.utils.PasswordHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private ITeacherRepository repository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void registerNewTeacher_ShouldThrowException_WhenNameIsBlank() throws DatabaseException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.registerNewTeacher("   ", "password123");
        });

        assertEquals("invalid teacher name", exception.getMessage());

        // we verify that the database was not called
        verify(repository, never()).save(any());
    }

    @Test
    void registerNewTeacher_ShouldThrowException_WhenPasswordIsTooShort() throws DatabaseException {
        String teacher = "Bob";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.registerNewTeacher(teacher, "1234");
        });

        assertEquals("password not strong enough", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void registerNewTeacher_ShouldThrowException_WhenTeacherAlreadyExists() throws DatabaseException {
        String teacherName = "Mark";
        // we simulate that the teacher is already in the DB
        when(repository.findByName(teacherName))
                .thenReturn(Optional.of(new TeacherDTO(UUID.randomUUID(), teacherName, "hash_password")));

        TeacherAlreadyExistsException exception = assertThrows(TeacherAlreadyExistsException.class, () -> {
            teacherService.registerNewTeacher(teacherName, "password");
        });

        assertEquals("Teacher already exists : " + teacherName, exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void registerNewTeacher_ShouldSucceed_WhenDataIsValid() throws DatabaseException {
        String teacherName = "John";

        when(repository.findByName(teacherName)).thenReturn(Optional.empty());

        // I originally used anyString() but the password needs to be at least some
        // characters long
        Teacher createdTeacher = teacherService.registerNewTeacher(teacherName, "12345678");

        assertNotNull(createdTeacher.getUuid());
        assertEquals(teacherName, createdTeacher.getName());

        verify(repository, times(1)).save(any(TeacherDTO.class));
    }

    @Test
    void teacherLogin_ShouldThrowException_WhenNameDoesNotExist() throws DatabaseException {
        String loginName = "Alex";
        when(repository.findByName(loginName)).thenReturn(Optional.empty());

        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> {
            teacherService.teacherLogin(loginName, "password");
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void teacherLogin_ShouldThrowException_WhenPasswordIsIncorrect() throws DatabaseException {
        String wrongPassword = "wrongPassword";
        String realPassword = "correctPassword";
        String hashedPasswordInDb = PasswordHasher.hashPassword(realPassword);
        String teacherName = "Phillipe";
        TeacherDTO dbTeacher = new TeacherDTO(UUID.randomUUID(), teacherName, hashedPasswordInDb);

        when(repository.findByName(teacherName)).thenReturn(Optional.of(dbTeacher));

        // try to connect with the wrong password
        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> {
            teacherService.teacherLogin(teacherName, wrongPassword);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void teacherLogin_ShouldSucceed_WhenCredentialsAreCorrect() throws DatabaseException {
        String password = "MyPassword";
        String hashedPasswordInDb = PasswordHasher.hashPassword(password);
        String teacherName = "David";
        UUID uuid = UUID.randomUUID();

        TeacherDTO dbTeacher = new TeacherDTO(uuid, teacherName, hashedPasswordInDb);

        when(repository.findByName(teacherName)).thenReturn(Optional.of(dbTeacher));

        Teacher loggedTeacher = teacherService.teacherLogin(teacherName, password);

        assertNotNull(loggedTeacher);
        assertEquals(uuid, loggedTeacher.getUuid());
        assertEquals(teacherName, loggedTeacher.getName());

    }
}
