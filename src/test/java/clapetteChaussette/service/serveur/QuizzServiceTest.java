package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.exception.repository.DatabaseException;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import be.helha.clapetteChaussette.server.service.QuizzService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizzServiceTest {

    @InjectMocks
    private QuizzService quizzService;

    @Mock
    private IQuizzRepository quizzRepository;

    @Mock
    private IQuestionRepository questionRepository;

    @Mock
    private QuizzDTO quizz;

    @Mock
    private QuestionDTO question1;

    @Mock
    private QuestionDTO question2;

    // ─── saveQuizz ────────────────────────────────────────────────

    @Test
    void saveQuizz_shouldSaveQuizzAndQuestions() throws DatabaseException {
        // GIVEN
        List<QuestionDTO> questions = List.of(question1, question2);

        // WHEN
        quizzService.saveQuizz(quizz, questions);

        // THEN
        verify(quizzRepository, times(1)).save(quizz);
        verify(questionRepository, times(1)).saveQuestions(questions);
    }

    @Test
    void saveQuizz_shouldThrowException_whenQuizzRepositoryFails() throws DatabaseException {
        // GIVEN
        List<QuestionDTO> questions = List.of(question1);

        doThrow(new DatabaseException("Erreur sauvegarde quizz",new RuntimeException("cause")))
                .when(quizzRepository).save(quizz);

        // WHEN + THEN
        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            quizzService.saveQuizz(quizz, questions);
        });

        assertEquals("Erreur sauvegarde quizz", exception.getMessage());

        // Les questions ne doivent pas être sauvegardées si le quizz échoue
        verify(questionRepository, never()).saveQuestions(any());
    }

    @Test
    void saveQuizz_shouldThrowException_whenQuestionRepositoryFails() throws DatabaseException {
        // GIVEN
        List<QuestionDTO> questions = List.of(question1);

        doThrow(new DatabaseException("Erreur sauvegarde questions",new RuntimeException("cause")))
                .when(questionRepository).saveQuestions(questions);

        // WHEN + THEN
        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            quizzService.saveQuizz(quizz, questions);
        });

        assertEquals("Erreur sauvegarde questions", exception.getMessage());

        // Le quizz a bien été sauvegardé avant l'erreur
        verify(quizzRepository, times(1)).save(quizz);
    }

    // ─── getTeacherQuizzes ────────────────────────────────────────

    @Test
    void getTeacherQuizzes_shouldReturnQuizzes_whenTeacherExists() throws DatabaseException {
        // GIVEN
        UUID teacherUuid = UUID.randomUUID();
        List<QuizzDTO> expectedQuizzes = List.of(quizz);

        when(quizzRepository.findAll(teacherUuid)).thenReturn(expectedQuizzes);

        // WHEN
        List<QuizzDTO> result = quizzService.getTeacherQuizzes(teacherUuid);

        // THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(expectedQuizzes, result)
        );

        verify(quizzRepository, times(1)).findAll(teacherUuid);
    }

    @Test
    void getTeacherQuizzes_shouldReturnEmptyList_whenTeacherHasNoQuizzes() throws DatabaseException {
        // GIVEN
        UUID teacherUuid = UUID.randomUUID();

        when(quizzRepository.findAll(teacherUuid)).thenReturn(List.of());

        // WHEN
        List<QuizzDTO> result = quizzService.getTeacherQuizzes(teacherUuid);

        // THEN
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getTeacherQuizzes_shouldThrowException_whenDatabaseFails() throws DatabaseException {
        // GIVEN
        UUID teacherUuid = UUID.randomUUID();

        when(quizzRepository.findAll(teacherUuid))
                .thenThrow(new DatabaseException("Erreur base de données",new RuntimeException("cause")));

        // WHEN + THEN
        assertThrows(DatabaseException.class, () -> {
            quizzService.getTeacherQuizzes(teacherUuid);
        });
    }

    // ─── getQuizzQuestions ────────────────────────────────────────

    @Test
    void getQuizzQuestions_shouldReturnQuestions_whenQuizzExists() throws DatabaseException {
        // GIVEN
        UUID quizzUuid = UUID.randomUUID();
        List<QuestionDTO> expectedQuestions = List.of(question1, question2);

        when(questionRepository.findByQuizz(quizzUuid)).thenReturn(expectedQuestions);

        // WHEN
        List<QuestionDTO> result = quizzService.getQuizzQuestions(quizzUuid);

        // THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(expectedQuestions, result)
        );

        verify(questionRepository, times(1)).findByQuizz(quizzUuid);
    }

    @Test
    void getQuizzQuestions_shouldReturnEmptyList_whenQuizzHasNoQuestions() throws DatabaseException {
        // GIVEN
        UUID quizzUuid = UUID.randomUUID();

        when(questionRepository.findByQuizz(quizzUuid)).thenReturn(List.of());

        // WHEN
        List<QuestionDTO> result = quizzService.getQuizzQuestions(quizzUuid);

        // THEN
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuizzQuestions_shouldThrowException_whenDatabaseFails() throws DatabaseException {
        // GIVEN
        UUID quizzUuid = UUID.randomUUID();

        when(questionRepository.findByQuizz(quizzUuid))
                .thenThrow(new DatabaseException("Erreur base de données",new RuntimeException("cause")));

        // WHEN + THEN
        assertThrows(DatabaseException.class, () -> {
            quizzService.getQuizzQuestions(quizzUuid);
        });
    }

}
