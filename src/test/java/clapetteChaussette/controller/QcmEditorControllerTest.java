package clapetteChaussette.controller;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.controller.editor.QcmEditorController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Anwser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QcmEditorControllerTest {

    private QcmEditorController controller = new QcmEditorController();

    @Test
    void testCreateQcm_ThrowsException_WhenTitleIsBlank() {
        List<Anwser> anwsers = Arrays.asList(new Anwser("A", true), new Anwser("B", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm("   ", anwsers));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenOneAnswerIsBlank() {
        String title = "Titre";
        List<Anwser> anwsers = Arrays.asList(new Anwser("A", true), new Anwser("  ", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm(title, anwsers));
    }

    @Test
    void testCreateQcm_ThrowsException_WhenNoCorrectAnswer() {
        String title = "Titre";
        List<Anwser> anwsers = Arrays.asList(new Anwser("A", true), new Anwser("B", false));

        assertThrows(InvalidQuestionException.class, () -> controller.createQcm(title, anwsers));
    }
}