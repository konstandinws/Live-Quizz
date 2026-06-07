package clapetteChaussette.service;

import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.Question;
import be.helha.clapetteChaussette.client.network.NetworkController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static be.helha.clapetteChaussette.common.models.enums.QuestionType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditorServiceTest {
    EditorService editorService;
    @Mock
    private NetworkController nc;
    @Mock
    private ValidateQuestionEditorService validateQuestionEditorService;

    @BeforeEach
    public void setup() {
        editorService = new EditorService(nc, validateQuestionEditorService);
    }

    @Test
    void testCreateOpenQuestion_withValidAnswers_shouldCreateQuestion() {
        // GIVEN
        String title = "question";
        List<String> answerTexts = List.of("1", "2", "3");
        List<Boolean> correctAnswers = List.of(true, false, false);
        boolean hasPoints = false;

        // WHEN
        Question question = editorService.createOpenQuestion(title, answerTexts, correctAnswers, hasPoints);

        // THEN
        List<String> actualAnswerStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualAnswerBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertAll(
                () -> assertEquals(title, question.getQuestion()),
                () -> assertEquals(answerTexts, actualAnswerStrings),
                () -> assertEquals(correctAnswers, actualAnswerBools),
                () -> assertEquals(OPEN, question.getType())
        );
    }

    @Test
    void testCreateMcqQuestion_withValidAnswers_shouldCreateQuestion() {
        // GIVEN
        String title = "question";
        List<String> answerTexts = List.of("1", "2", "3");
        List<Boolean> correctAnswers = List.of(true, false, false);

        // WHEN
        Question question = editorService.createMcqQuestion(title, answerTexts, correctAnswers);

        // THEN
        List<String> actualAnswerStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualAnswerBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertAll(
                () -> assertEquals(title, question.getQuestion()),
                () -> assertEquals(answerTexts, actualAnswerStrings),
                () -> assertEquals(correctAnswers, actualAnswerBools),
                () -> assertEquals(MCQ, question.getType())
        );
    }

    @Test
    void testCreatePollQuestion_withValidAnswers_shouldCreateQuestion() {
        // GIVEN
        String title = "Quelle est ta couleur préférée ?";
        List<String> answers = List.of("Rouge", "Bleu", "Vert");

        // WHEN
        Question question = editorService.createPollQuestion(title, answers);

        // THEN
        assertAll(
                () -> assertNotNull(question),
                () -> assertEquals(title, question.getQuestion()),
                () -> assertEquals(answers.size(), question.getAnswers().size())
        );
        verify(validateQuestionEditorService, times(1)).validatePollQuestion(title, answers);
    }

    @Test
    void testCreateOpenQuestion_withEmptyAnswers_shouldThrowException() {
        // GIVEN
        String title = "question";
        List<String> answerTexts = List.of();
        List<Boolean> correctAnswers = List.of();
        doThrow(new InvalidQuestionException("Liste de réponses vide"))
                .when(validateQuestionEditorService).validateOpenQuestion(title, answerTexts);

        // WHEN + THEN
        assertThrows(InvalidQuestionException.class, () ->
            editorService.createOpenQuestion(title, answerTexts, correctAnswers, false)
        );
    }

    @Test
    void testCreateOpenQuestion_withInvalidTitle_shouldThrowException() {
        // GIVEN
        String title = "";
        List<String> answerTexts = List.of("1", "2");
        List<Boolean> correctAnswers = List.of(true, false);
        doThrow(new InvalidQuestionException("Titre invalide"))
                .when(validateQuestionEditorService).validateOpenQuestion(title, answerTexts);

        // WHEN + THEN
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () ->
            editorService.createOpenQuestion(title, answerTexts, correctAnswers, false)
        );

        assertEquals("Titre invalide", exception.getMessage());
    }

    @Test
    void testCreateOpenQuestion_withValidAnswers_shouldMapCorrectly() throws InvalidQuestionException {
        // GIVEN
        String title = "Question test";
        List<String> answerTexts = List.of("1", "2", "3");
        List<Boolean> correctAnswers = List.of(true, false, false);
        doNothing().when(validateQuestionEditorService).validateOpenQuestion(title, answerTexts);

        // WHEN
        Question question = editorService.createOpenQuestion(title, answerTexts, correctAnswers, false);

        // THEN
        List<String> actualStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertAll(
                () -> assertEquals(answerTexts, actualStrings),
                () -> assertEquals(correctAnswers, actualBools)
        );
    }

    @Test
    void testCreateMcqQuestion_withValidAnswers_shouldMapAnswersCorrectly() throws InvalidQuestionException {
        // GIVEN
        String title = "Question test";
        List<String> answers = List.of("A", "B", "C");
        List<Boolean> correctAnswers = List.of(false, true, false);
        doNothing().when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN
        Question question = editorService.createMcqQuestion(title, answers, correctAnswers);

        // THEN
        List<String> actualStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertAll(
                () -> assertEquals(answers, actualStrings),
                () -> assertEquals(correctAnswers, actualBools)
        );
    }

    @Test
    void testCreateMcqQuestion_withInvalidTitle_shouldThrowException() throws InvalidQuestionException {
        // GIVEN
        String title = "";
        List<String> answers = List.of("A", "B");
        List<Boolean> correctAnswers = List.of(true, false);
        doThrow(new InvalidQuestionException("Titre invalide"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () ->
            editorService.createMcqQuestion(title, answers, correctAnswers)
        );
        assertEquals("Titre invalide", exception.getMessage());
    }

    @Test
    void testCreateMcqQuestion_withEmptyAnswers_shouldThrowException() throws InvalidQuestionException {
        // GIVEN
        String title = "Question valide";
        List<String> answers = List.of();
        List<Boolean> correctAnswers = List.of();
        doThrow(new InvalidQuestionException("Liste de réponses vide"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        assertThrows(InvalidQuestionException.class, () ->
            editorService.createMcqQuestion(title, answers, correctAnswers)
        );
    }

    @Test
    void testCreateMcqQuestion_withNoCorrectAnswer_shouldThrowException() throws InvalidQuestionException {
        // GIVEN
        String title = "Question valide";
        List<String> answers = List.of("A", "B", "C");
        List<Boolean> correctAnswers = List.of(false, false, false); // No correct answer
        doThrow(new InvalidQuestionException("Aucune bonne réponse définie"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () ->
            editorService.createMcqQuestion(title, answers, correctAnswers)
        );
        assertEquals("Aucune bonne réponse définie", exception.getMessage());
    }
}
