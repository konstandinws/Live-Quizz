package be.helha.clapetteChaussette.service;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.model.question.OpenQuestion;

public class EditorService {
    /**
     * This service will be responsible for all the business logic related to the
     * editor features.
     * It will create the question objects and save them to the database
     */

    public OpenQuestion createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints) {
        List<Answer> answers = answerTexts.stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> new Answer(text, true)) // by default all the answers given are correct
                .toList();

        return new OpenQuestion(title, answers, hasPoints);
    }

    public McqQuestion createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers) {

        List<Answer> answerObjects = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            answerObjects.add(new Answer(answers.get(i), correctAnswers.get(i)));
        }

        List<String> correctAnswerTexts = answerObjects.stream()
                .filter(Answer::isCorrect)
                .map(Answer::getAnswer)
                .toList();

        return new McqQuestion(title, answerObjects);
    }

}
