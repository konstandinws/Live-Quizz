package clapetteChaussette.controller;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.controller.editor.McqEditorController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class McqEditorControllerTest {

    private McqEditorController controller = new McqEditorController();

    @Test
    void testCreateQcm_ThrowsException_WhenTitleIsBlank() {
        List<String> answerTexts = Arrays.asList("A", "B");
        List<Boolean> correctFlags = Arrays.asList(true, false);

        assertThrows(InvalidQuestionException.class, () -> controller.createMcq("   ", answerTexts, correctFlags));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenOneAnswerIsBlank() {
        String title = "Titre";
        List<String> answerTexts = Arrays.asList("A", "  ");
        List<Boolean> correctFlags = Arrays.asList(true, false);

        assertThrows(InvalidQuestionException.class, () -> controller.createMcq(title, answerTexts, correctFlags));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenNoCorrectAnswer() {
        String title = "Titre";
        List<String> answerTexts = Arrays.asList("A", "B");
        List<Boolean> correctFlags = Arrays.asList(false, false);

        assertThrows(InvalidQuestionException.class, () -> controller.createMcq(title, answerTexts, correctFlags));
    }
}