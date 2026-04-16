package clapetteChaussette.controller;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.controller.editor.QcmEditorController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QcmEditorControllerTest {

    private QcmEditorController controller = new QcmEditorController();

    @Test
    void testCreateQcm_ThrowsException_WhenTitleIsBlank() {
        List<Answer> answers = Arrays.asList(new Answer("A", true), new Answer("B", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm("   ", answers));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenOneAnswerIsBlank() {
        String title = "Titre";
        List<Answer> answers = Arrays.asList(new Answer("A", true), new Answer("  ", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm(title, answers));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenNoCorrectAnswer() {
        String title = "Titre";
        List<Answer> answers = Arrays.asList(new Answer("A", true), new Answer("B", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm(title, answers));
    }
}