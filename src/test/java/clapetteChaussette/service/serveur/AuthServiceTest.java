package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.common.exception.login.LoginFailedException;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.server.repository.TeacherService;
import be.helha.clapetteChaussette.server.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private Teacher teacher;

    // ─── login ────────────────────────────────────────────────────

    @Test
    void login_shouldReturnTeacher_whenCredentialsAreValid() throws LoginFailedException, DatabaseException {
        // GIVEN
        when(teacherService.teacherLogin("Alice", "password123")).thenReturn(teacher);

        // WHEN
        Teacher result = authService.login("Alice", "password123");

        // THEN
        assertNotNull(result);
        assertEquals(teacher, result);
        verify(teacherService, times(1)).teacherLogin("Alice", "password123");
    }

    @Test
    void login_shouldThrowLoginFailedException_whenCredentialsAreInvalid() throws LoginFailedException, DatabaseException {
        // GIVEN
        when(teacherService.teacherLogin("Alice", "wrongpassword"))
                .thenThrow(new LoginFailedException());

        // WHEN + THEN
        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> {
            authService.login("Alice", "wrongpassword");
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void login_shouldThrowDatabaseException_whenDatabaseFails() throws LoginFailedException, DatabaseException {
        // GIVEN
        when(teacherService.teacherLogin("Alice", "password123"))
                .thenThrow(new DatabaseException("Erreur base de données", new RuntimeException("cause")));

        // WHEN + THEN
        assertThrows(DatabaseException.class, () -> {
            authService.login("Alice", "password123");
        });
    }

    @Test
    void login_shouldThrowLoginFailedException_whenNameIsEmpty() throws LoginFailedException, DatabaseException {
        // GIVEN
        when(teacherService.teacherLogin("", "password123"))
                .thenThrow(new LoginFailedException());

        // WHEN + THEN
        assertThrows(LoginFailedException.class, () -> {
            authService.login("", "password123");
        });
    }

    // ─── register ─────────────────────────────────────────────────

    @Test
    void register_shouldReturnTeacher_whenDataIsValid() throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        // GIVEN
        when(teacherService.registerNewTeacher("Alice", "password123")).thenReturn(teacher);

        // WHEN
        Teacher result = authService.register("Alice", "password123");

        // THEN
        assertNotNull(result);
        assertEquals(teacher, result);
        verify(teacherService, times(1)).registerNewTeacher("Alice", "password123");
    }

    @Test
    void register_shouldThrowTeacherAlreadyExistsException_whenTeacherAlreadyExists() throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        // GIVEN
        when(teacherService.registerNewTeacher("Alice", "password123"))
                .thenThrow(new TeacherAlreadyExistsException("Alice"));

        // WHEN + THEN
        TeacherAlreadyExistsException exception = assertThrows(TeacherAlreadyExistsException.class, () -> {
            authService.register("Alice", "password123");
        });

        assertEquals("Teacher already exists : Alice", exception.getMessage());
    }

    @Test
    void register_shouldThrowIllegalArgumentException_whenDataIsInvalid() throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        // GIVEN
        when(teacherService.registerNewTeacher("", "password123"))
                .thenThrow(new IllegalArgumentException("Nom invalide"));

        // WHEN + THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register("", "password123");
        });

        assertEquals("Nom invalide", exception.getMessage());
    }

    @Test
    void register_shouldThrowDatabaseException_whenDatabaseFails() throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        // GIVEN
        when(teacherService.registerNewTeacher("Alice", "password123"))
                .thenThrow(new DatabaseException("Erreur base de données", new RuntimeException("cause")));

        // WHEN + THEN
        assertThrows(DatabaseException.class, () -> {
            authService.register("Alice", "password123");
        });
    }

    @Test
    void register_shouldThrowIllegalArgumentException_whenPasswordIsEmpty() throws TeacherAlreadyExistsException, IllegalArgumentException, DatabaseException {
        // GIVEN
        when(teacherService.registerNewTeacher("Alice", ""))
                .thenThrow(new IllegalArgumentException("Mot de passe invalide"));

        // WHEN + THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register("Alice", "");
        });

        assertEquals("Mot de passe invalide", exception.getMessage());
    }
}
