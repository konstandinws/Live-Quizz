package clapetteChaussette.model.question;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.model.question.QcmQuestion;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QcmQuestionTest {

    @Test
    void unique_answer_constructor() {

        String title = "Que vaut 15 + 3";
        List<String> choices = Arrays.asList("12", "10", "18");
        List<String> correct = Arrays.asList("18");

        QcmQuestion question = new QcmQuestion(title, choices, correct);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correct, question.getCorrectAnswers());

        assertFalse(question.isMultipleAnswers());
    }

    @Test
    void multiple_answers_constructor() {

        String title = "Quels sont les nombres pairs ?";
        List<String> choices = Arrays.asList("1", "2", "3", "4");
        List<String> corrects = Arrays.asList("2", "4");

        QcmQuestion question = new QcmQuestion(title, choices, corrects);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(corrects, question.getCorrectAnswers());

        assertTrue(question.isMultipleAnswers());
    }
}