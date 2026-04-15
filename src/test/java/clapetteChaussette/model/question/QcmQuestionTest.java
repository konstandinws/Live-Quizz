package clapetteChaussette.model.question;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.model.question.Anwser;
import be.helha.clapetteChaussette.model.question.QcmQuestion;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QcmQuestionTest {

    @Test
    void unique_answer_constructor() {

        String title = "Que vaut 15 + 3";
        List<Anwser> choices = Arrays.asList(new Anwser("12", false), new Anwser("10", false), new Anwser("18", true));
        List<Anwser> correctAnswers = List.of(new Anwser("18", true));

        QcmQuestion question = new QcmQuestion(title, choices);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correctAnswers, question.getCorrectAnswers());

        assertFalse(question.isMultipleAnswers());
    }

    @Test
    void multiple_answers_constructor() {

        String title = "Quels sont les nombres pairs ?";
        List<Anwser> choices = Arrays.asList(new Anwser("1", false), new Anwser("2", true), new Anwser("3", false),
                new Anwser("4", true));
        List<Anwser> correctAnswers = Arrays.asList(new Anwser("2", true), new Anwser("4", true));

        QcmQuestion question = new QcmQuestion(title, choices);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correctAnswers, question.getCorrectAnswers());

        assertTrue(question.isMultipleAnswers());
    }
}