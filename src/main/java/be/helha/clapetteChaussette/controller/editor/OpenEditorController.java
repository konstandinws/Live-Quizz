package be.helha.clapetteChaussette.controller.editor;

import java.util.List;

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Anwser;
import be.helha.clapetteChaussette.model.question.OpenQuestion;

public class OpenEditorController implements IViewController {

    EditorController mainController;

    public void setController(EditorController mainController) {
        this.mainController = mainController;
    }

    public void createOpenQuestion(String title, List<Anwser> correctAnswers, boolean hasPoints)
            throws InvalidQuestionException {

        if (title == null || title.isBlank()) {
            throw new InvalidQuestionException("La question ne peut pas être vide.");
        }

        OpenQuestion oq = new OpenQuestion(title, correctAnswers, hasPoints);
        mainController.addQuestionToQuizz(oq);
        mainController.navigateToQuizzEditor();
    }

}