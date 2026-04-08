package clapetteChaussette.controller;

import clapetteChaussette.exception.InvalidQuestionException;
import clapetteChaussette.model.question.OpenQuestion;
import java.util.List;

public class OpenEditorController implements IController {

    public OpenQuestion createOpenQuestion(String title, List<String> correctAnswers, boolean hasPoints)
            throws InvalidQuestionException {

        if (title == null || title.isBlank()) {
            throw new InvalidQuestionException("La question ne peut pas être vide.");
        }

        OpenQuestion oq = new OpenQuestion(title);
        oq.setCorrectAnswers(correctAnswers);
        oq.setPointsEnabled(hasPoints);

        return oq;
    }
}