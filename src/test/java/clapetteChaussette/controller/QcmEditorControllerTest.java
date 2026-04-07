package clapetteChaussette.controller;

import clapetteChaussette.exception.InvalidQuestionException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QcmEditorControllerTest {

    private QcmEditorController controller = new QcmEditorController();

    @Test
    void testCreateQcm_ThrowsException_WhenTitleIsBlank() {
        List<String> answers = Arrays.asList("A", "B");
        List<String> correctAnswers = List.of("A");

        assertThrows(InvalidQuestionException.class, () ->
                controller.createQcm("   ", answers, correctAnswers)
        );
    }

    @Test
    void testCreateQcm_ThrowsException_WhenOneAnswerIsBlank() {
        String title = "Titre";
        List<String> answers = Arrays.asList("A", "  ");
        List<String> correctAnswers = List.of("A");

        assertThrows(InvalidQuestionException.class, () ->
                controller.createQcm(title, answers, correctAnswers)
        );
    }

    @Test
    void testCreateQcm_ThrowsException_WhenNoCorrectAnswer() {
        String title = "Titre";
        List<String> answers = Arrays.asList("A", "B");
        List<String> correctAnswers = Collections.emptyList();

        assertThrows(InvalidQuestionException.class, () ->
                controller.createQcm(title, answers, correctAnswers)
        );
    }
}