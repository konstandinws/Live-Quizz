package clapetteChaussette.service;

import be.helha.clapetteChaussette.exception.TeacherAlreadyExistsException;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private TeacherRepository repository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void registerNewTeacher_ShouldThrowException_WhenNameIsBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.registerNewTeacher("   ", "password123");
        });

        assertEquals("invalid teacher name", exception.getMessage());

        // we verify that the database was not called
        verify(repository, never()).save(any());
    }

    @Test
    void registerNewTeacher_ShouldThrowException_WhenPasswordIsTooShort() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.registerNewTeacher(anyString(), "1234");
        });

        assertEquals("password not strong enough", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void registerNewTeacher_ShouldThrowException_WhenTeacherAlreadyExists() {
        String teacherName = "test_teacher";
        // we simulate that the teacher is already in the DB
        when(repository.findByName(teacherName))
                .thenReturn(Optional.of(new TeacherDTO(UUID.randomUUID(), teacherName, "hash_password")));

        TeacherAlreadyExistsException exception = assertThrows(TeacherAlreadyExistsException.class, () -> {
            teacherService.registerNewTeacher(teacherName, "password");
        });

        assertEquals("This teacher already exist", exception.getMessage());

        verify(repository, never()).save(any());
    }
}
