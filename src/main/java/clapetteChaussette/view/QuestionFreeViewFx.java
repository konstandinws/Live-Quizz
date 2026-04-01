package clapetteChaussette.view;

import clapetteChaussette.model.question.QuestionFree;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionFreeViewFx {

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private void initialize() {

        QuestionFree qst = new QuestionFree("Comment tu trouves Brahim physiquement ?");

        questionFree.setText(qst.getQuestion());
    }
}