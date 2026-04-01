package clapetteChaussette.view;

import clapetteChaussette.model.question.OpenQuestion;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionFreeViewFX {

    private IFreeListener listener;
    private OpenQuestion questionData;

    @FXML
    private Label questionFree;
    @FXML
    private TextArea textArea;

    public void set (IFreeListener listener, OpenQuestion question) {
        this.listener = listener;
        this.questionData = question;
        this.questionFree.setText(question.getQuestion());
    }

    @FXML
    private void handleSendAction(ActionEvent event) {
        String userResponse = textArea.getText();
        if (listener != null) {
            listener.validate(userResponse);
        }
    }


}