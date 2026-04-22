package clapetteChaussette.service;

import be.helha.clapetteChaussette.exception.InvalidQuizzException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.QcmQuestion;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.service.QuizzService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizzServiceTest {

    @Mock
    private QuizzRepository quizzRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizzService quizzService;

    @Test
    void createQuizz_ShouldThrowException_WhenNameIsBlank() {
        InvalidQuizzException exception = assertThrows(InvalidQuizzException.class, () -> {
            quizzService.createQuizz("   ");
        });

        assertEquals("Le nom du quiz ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void createQuizz_ShouldThrowException_WhenNameIsNull() {
        InvalidQuizzException exception = assertThrows(InvalidQuizzException.class, () -> {
            quizzService.createQuizz(null);
        });

        assertEquals("Le nom du quiz ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void createQuizz_ShouldReturnNewQuizz_WhenNameIsValid() {
        String quizzName = "Histoire de France";

        Quizz newQuizz = quizzService.createQuizz(quizzName);

        assertNotNull(newQuizz);
        assertEquals(quizzName, newQuizz.getName());
        assertNotNull(newQuizz.getUuid());
        // the quizz should be empty at the creation
        assertTrue(newQuizz.getQuestions().isEmpty());
    }

    @Test
    void findAllQuizzes_ShouldThrowException_WhenTeacherUuidIsNull() {
        InvalidQuizzException exception = assertThrows(InvalidQuizzException.class, () -> {
            quizzService.findAllQuizzes(null);
        });

        assertEquals("L'identifiant du professeur est requis.", exception.getMessage());
        verify(quizzRepository, never()).findAll(any());
    }

    @Test
    void findAllQuizzes_ShouldReturnListOfQuizzes_WhenValid() {
        UUID teacherUuid = UUID.randomUUID();
        List<QuizzDTO> expectedQuizzes = List.of(
                new QuizzDTO(UUID.randomUUID(), "Math", teacherUuid),
                new QuizzDTO(UUID.randomUUID(), "Science", teacherUuid)
        );

        when(quizzRepository.findAll(teacherUuid)).thenReturn(expectedQuizzes);

        List<QuizzDTO> result = quizzService.findAllQuizzes(teacherUuid);

        assertEquals(2, result.size());
        verify(quizzRepository, times(1)).findAll(teacherUuid);
    }

    @Test
    void registerNewQuizz_ShouldThrowException_WhenTeacherUuidIsNull() {
        Quizz quizz = new Quizz("Mon super Quiz");

        InvalidQuizzException exception = assertThrows(InvalidQuizzException.class, () -> {
            quizzService.registerNewQuizz(quizz, null);
        });

        assertEquals("Un quiz doit être lié à un professeur.", exception.getMessage());

        verify(quizzRepository, never()).save(any());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void registerNewQuizz_ShouldSaveQuizzAndAllQuestions_WhenValid() {
        UUID teacherUuid = UUID.randomUUID();
        Quizz quizz = new Quizz("Culture Générale");

        List<Answer> answersQ1 = List.of(
                new Answer("Paris", true),
                new Answer("Londres", false)
        );
        List<Answer> answersQ2 = List.of(
                new Answer("4", true),
                new Answer("5", false)
        );

        QcmQuestion q1 = new QcmQuestion("Quelle est la capitale de la France?", answersQ1);
        QcmQuestion q2 = new QcmQuestion("Combien font 2+2?", answersQ2);

        quizz.addQuestion(q1);
        quizz.addQuestion(q2);

        quizzService.registerNewQuizz(quizz, teacherUuid);

        verify(quizzRepository, times(1)).save(any(QuizzDTO.class));

        verify(questionRepository, times(2)).save(any(QuestionDTO.class));
    }
}
