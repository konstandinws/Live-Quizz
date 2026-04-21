package clapetteChaussette.service;

import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
}
