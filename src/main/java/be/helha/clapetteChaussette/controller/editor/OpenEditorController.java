package be.helha.clapetteChaussette.controller.editor;

import java.util.List;

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.OpenQuestion;

public class OpenEditorController implements IViewController {

    EditorController mainController;

    public void setController(EditorController mainController) {
        this.mainController = mainController;
    }

    public void createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints)
            throws InvalidQuestionException {

        if (title == null || title.isBlank()) {
            throw new InvalidQuestionException("La question ne peut pas être vide.");
        }

        List<Answer> answers = answerTexts.stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> new Answer(text, true)) // by default all the answers given are correct
                .toList();

        OpenQuestion oq = new OpenQuestion(title, answers, hasPoints);
        mainController.addQuestionToQuizz(oq);
        mainController.navigateToQuizzEditor();
    }

}