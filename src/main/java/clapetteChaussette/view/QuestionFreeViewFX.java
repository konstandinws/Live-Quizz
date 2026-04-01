package clapetteChaussette.view;

import clapetteChaussette.model.question.OpenQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionFreeViewFX {

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    private IOpenListener listener;

    @FXML
    private void initialize() {
        // On ne crée plus la question ici, on attend qu'elle vienne du MainController
    }


    public void set(IOpenListener listener, OpenQuestion question) {
        this.listener = listener;
        if (question != null && questionFree != null) {
            this.questionFree.setText(question.getQuestion());
        }
    }


    @FXML
    private void handleSendAction() {
        if (listener != null && textArea != null) {
            listener.validate(textArea.getText());
        }
    }
}