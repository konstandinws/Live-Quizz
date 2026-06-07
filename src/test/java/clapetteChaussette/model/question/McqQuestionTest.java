package clapetteChaussette.model.question;

import org.junit.jupiter.api.Test;

import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.McqQuestion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println(question.getCorrectAnswers().get(0).getAnswer());
        assertEquals(correctAnswers.get(0).getAnswer(), question.getCorrectAnswers().get(0).getAnswer());

        assertFalse(question.isMultipleAnswers());
    }

    @Test
    void multiple_answers_constructor() {

        String title = "Quels sont les nombres pairs ?";
        List<Answer> choices = Arrays.asList(new Answer("1", false), new Answer("2", true), new Answer("3", false),
                new Answer("4", true));

        McqQuestion question = new McqQuestion(title, choices);
        List<String> actualCorrectTexts = question.getCorrectAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
        List<String> expectedCorrectTexts = List.of("2", "4");
        assertAll(
                () -> assertEquals(title, question.getQuestion()),
                () -> assertEquals(choices.size(), question.getAnswers().size()),
                () -> assertEquals(expectedCorrectTexts, actualCorrectTexts),
                () -> assertTrue(question.isMultipleAnswers())
        );

        assertTrue(question.isMultipleAnswers());
    }
}