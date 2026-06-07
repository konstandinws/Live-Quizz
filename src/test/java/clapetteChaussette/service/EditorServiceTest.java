package clapetteChaussette.service;

import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.OpenQuestion;
import be.helha.clapetteChaussette.common.models.questions.Question;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;

import java.util.*;
import java.util.stream.Collectors;

import static be.helha.clapetteChaussette.common.models.enums.QuestionType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditorServiceTest {
    EditorService  editorService;
    @Mock
    private IQuizzRepository quizzRepository;
    @Mock
    private IQuestionRepository questionRepository;
    @Mock
    private ValidateQuestionEditorService validateQuestionEditorService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        editorService = new EditorService(quizzRepository, questionRepository, validateQuestionEditorService);
    }
    @Test
    public void createOpenQuestiontest(){
        List<String> listeanswerstring = new ArrayList<String>();
        listeanswerstring.add("1");
        listeanswerstring.add("2");
        listeanswerstring.add("3");
        ArrayList<Boolean> listeenswerbool = new ArrayList<Boolean>();
        listeenswerbool.add(true);
        listeenswerbool.add(false);
        listeenswerbool.add(false);
        Question question = editorService.createOpenQuestion("question",listeanswerstring,listeenswerbool,false );
        List<String> actualAnswerStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualAnswerBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertEquals(question.getQuestion(),"question");
        assertEquals(actualAnswerStrings,listeanswerstring);
        assertEquals(actualAnswerBools,listeenswerbool);
        assertEquals(question.getType(),OPEN);
    }
    @Test
    public void createMCQquestiontest(){
        List<String> listeanswerstring = new ArrayList<String>();
        listeanswerstring.add("1");
        listeanswerstring.add("2");
        listeanswerstring.add("3");
        ArrayList<Boolean> listeenswerbool = new ArrayList<Boolean>();
        listeenswerbool.add(true);
        listeenswerbool.add(false);
        listeenswerbool.add(false);
        Question question = editorService.createMcqQuestion("question",listeanswerstring,listeenswerbool );
        List<String> actualAnswerStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<Boolean> actualAnswerBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());
        assertEquals(question.getQuestion(),"question");
        assertEquals(actualAnswerStrings,listeanswerstring);
        assertEquals(actualAnswerBools,listeenswerbool);
        assertEquals(question.getType(),MCQ);
    }
    @Test
    public void createPollquestiontest(){
        String title = "Quelle est ta couleur préférée ?";
        List<String> answers = List.of("Rouge", "Bleu", "Vert");

        // WHEN
        Question question = editorService.createPollQuestion(title, answers);

        // THEN
        assertNotNull(question);
        assertEquals(title, question.getQuestion());
        assertEquals(answers.size(), question.getAnswers().size());

        // Vérifie que la validation a bien été appelée
        verify(validateQuestionEditorService, times(1)).validatePollQuestion(title, answers);
    }
    @Test
    public void createOpenquestionemptytest(){
        List<String> listeanswerstring = new ArrayList<>();
        List<Boolean> listeenswerbool = new ArrayList<>();
        doThrow(new InvalidQuestionException("Liste de réponses vide"))
                .when(validateQuestionEditorService).validateOpenQuestion("question", listeanswerstring);
        assertThrows(InvalidQuestionException.class, () -> {
            editorService.createOpenQuestion("question", listeanswerstring, listeenswerbool, false);
        });
        // WHEN + THEN

    }
    @Test
    public void createOpenquestiontitleivalidtest(){
        List<String> listeanswerstring = new ArrayList<>();
        listeanswerstring.add("1");
        listeanswerstring.add("2");
        List<Boolean> listeenswerbool = new ArrayList<>();
        listeenswerbool.add(true);
        listeenswerbool.add(false);
        doThrow(new InvalidQuestionException("Titre invalide"))
                .when(validateQuestionEditorService).validateOpenQuestion("", listeanswerstring);

        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            editorService.createOpenQuestion("", listeanswerstring, listeenswerbool, false);
        });

        assertEquals("Titre invalide", exception.getMessage());

    }
    @Test
    void createOpenQuestion_shouldMapCorrectAnswers() throws InvalidQuestionException {
        // GIVEN
        String title = "Question test";
        List<String> answerTexts = List.of("1", "2", "3");
        List<Boolean> correctAnswers = List.of(true, false, false);
        boolean hasPoints = false;

        doNothing().when(validateQuestionEditorService).validateOpenQuestion(title, answerTexts);

        // WHEN
        Question question = editorService.createOpenQuestion(title, answerTexts, correctAnswers, hasPoints);

        // Extraction
        List<String> actualStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());

        List<Boolean> actualBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());

        // THEN
        assertAll(
                () -> assertEquals(answerTexts, actualStrings),
                () -> assertEquals(correctAnswers, actualBools)
        );
    }
    @Test
    void createMcqQuestion_shouldMapAnswersCorrectly() throws InvalidQuestionException {
        // GIVEN
        String title = "Question test";
        List<String> answers = List.of("A", "B", "C");
        List<Boolean> correctAnswers = List.of(false, true, false);

        doNothing().when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN
        Question question = editorService.createMcqQuestion(title, answers, correctAnswers);

        List<String> actualStrings = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());

        List<Boolean> actualBools = question.getAnswers().stream()
                .map(Answer::isCorrect)
                .collect(Collectors.toList());

        // THEN
        assertAll(
                () -> assertEquals(answers, actualStrings),
                () -> assertEquals(correctAnswers, actualBools)
        );
    }
    @Test
    void createMcqQuestion_shouldThrowException_whenTitleIsInvalid() throws InvalidQuestionException {
        // GIVEN
        String title = "";
        List<String> answers = List.of("A", "B");
        List<Boolean> correctAnswers = List.of(true, false);

        doThrow(new InvalidQuestionException("Titre invalide"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            editorService.createMcqQuestion(title, answers, correctAnswers);
        });

        assertEquals("Titre invalide", exception.getMessage());
    }
    @Test
    void createMcqQuestion_shouldThrowException_whenAnswersIsEmpty() throws InvalidQuestionException {
        // GIVEN
        String title = "Question valide";
        List<String> answers = List.of();
        List<Boolean> correctAnswers = List.of();

        doThrow(new InvalidQuestionException("Liste de réponses vide"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        assertThrows(InvalidQuestionException.class, () -> {
            editorService.createMcqQuestion(title, answers, correctAnswers);
        });
    }
    @Test
    void createMcqQuestion_shouldThrowException_whenNoCorrectAnswer() throws InvalidQuestionException {
        // GIVEN
        String title = "Question valide";
        List<String> answers = List.of("A", "B", "C");
        List<Boolean> correctAnswers = List.of(false, false, false); // aucune bonne réponse

        doThrow(new InvalidQuestionException("Aucune bonne réponse définie"))
                .when(validateQuestionEditorService)
                .validateMcqQuestion(title, answers, correctAnswers);

        // WHEN + THEN
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            editorService.createMcqQuestion(title, answers, correctAnswers);
        });

        assertEquals("Aucune bonne réponse définie", exception.getMessage());
    }
}
