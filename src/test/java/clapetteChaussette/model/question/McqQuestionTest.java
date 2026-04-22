package clapetteChaussette.model.question;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class McqQuestionTest {

    @Test
    void unique_answer_constructor() {

        String title = "Que vaut 15 + 3";
        List<Answer> choices = Arrays.asList(new Answer("12", false), new Answer("10", false), new Answer("18", true));
        List<Answer> correctAnswers = List.of(new Answer("18", true));

        McqQuestion question = new McqQuestion(title, choices);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correctAnswers, question.getCorrectAnswers());

        assertFalse(question.isMultipleAnswers());
    }

    @Test
    void multiple_answers_constructor() {

        String title = "Quels sont les nombres pairs ?";
        List<Answer> choices = Arrays.asList(new Answer("1", false), new Answer("2", true), new Answer("3", false),
                new Answer("4", true));
        List<Answer> correctAnswers = Arrays.asList(new Answer("2", true), new Answer("4", true));

        McqQuestion question = new McqQuestion(title, choices);

        assertEquals(title, question.getQuestion());
        assertEquals(choices, question.getAnswers());
        assertEquals(correctAnswers, question.getCorrectAnswers());

        assertTrue(question.isMultipleAnswers());
    }
}