package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.server.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;
    @BeforeEach
    void setUp() {
        questionService = new QuestionService();
    }

    // ─── hideCorrectAnswers ───────────────────────────────────────

    @Test
    void hideCorrectAnswers_shouldSetAllAnswersToFalse() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        UUID quizzUuid = UUID.randomUUID();

        List<Answer> answers = List.of(
                new Answer("Paris", true),
                new Answer("Londres", false),
                new Answer("Berlin", true)
        );

        QuestionDTO question = new QuestionDTO(uuid, quizzUuid, "Capitale ?", answers, QuestionType.MCQ);

        // WHEN
        QuestionDTO result = questionService.hideCorrectAnswers(question);

        // THEN
        List<Boolean> allBools = result.answers().stream()
                .map(Answer::isCorrect)
                .toList();

        assertTrue(allBools.stream().noneMatch(b -> b)); // tous à false
    }

    @Test
    void hideCorrectAnswers_shouldKeepAnswerTexts() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        UUID quizzUuid = UUID.randomUUID();

        List<Answer> answers = List.of(
                new Answer("Paris", true),
                new Answer("Londres", false)
        );

        QuestionDTO question = new QuestionDTO(uuid, quizzUuid, "Capitale ?", answers, QuestionType.MCQ);

        // WHEN
        QuestionDTO result = questionService.hideCorrectAnswers(question);

        // THEN — les textes sont conservés
        List<String> actualTexts = result.answers().stream()
                .map(Answer::getAnswer)
                .toList();

        assertEquals(List.of("Paris", "Londres"), actualTexts);
    }

    @Test
    void hideCorrectAnswers_shouldKeepAllOtherFields() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        UUID quizzUuid = UUID.randomUUID();

        List<Answer> answers = List.of(new Answer("Paris", true));
        QuestionDTO question = new QuestionDTO(uuid, quizzUuid, "Capitale ?", answers, QuestionType.MCQ);

        // WHEN
        QuestionDTO result = questionService.hideCorrectAnswers(question);

        // THEN — les autres champs sont inchangés
        assertAll(
                () -> assertEquals(uuid, result.uuid()),
                () -> assertEquals(quizzUuid, result.quizzUuid()),
                () -> assertEquals("Capitale ?", result.title()),
                () -> assertEquals(QuestionType.MCQ, result.type())
        );
    }

    @Test
    void hideCorrectAnswers_shouldReturnEmptyList_whenNoAnswers() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        UUID quizzUuid = UUID.randomUUID();

        QuestionDTO question = new QuestionDTO(uuid, quizzUuid, "Capitale ?", List.of(), QuestionType.MCQ);

        // WHEN
        QuestionDTO result = questionService.hideCorrectAnswers(question);

        // THEN
        assertNotNull(result);
        assertTrue(result.answers().isEmpty());
    }
}
