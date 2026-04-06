package clapetteChaussette.model.question;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class QcmQuestionTest {

    @Test
    void constructeur_question_reponse_unique() {

        String title = "Que vaut 15 + 3";
        List<String> choices = Arrays.asList("12", "10", "18");
        String correct = "18";

        QcmQuestion question = new QcmQuestion(title, choices, correct);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correct, question.getCorrectAnswer());

        assertFalse(question.isChoixMultiple());
    }

    @Test
    void constructeur_question_reponse_multiple() {

        String title = "Quels sont les nombres pairs ?";
        List<String> choices = Arrays.asList("1", "2", "3", "4");
        List<String> corrects = Arrays.asList("2", "4");

        QcmQuestion question = new QcmQuestion(title, choices, corrects);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(corrects, question.getCorrectAnswers());

        assertTrue(question.isChoixMultiple());
    }
}