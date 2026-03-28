package clapetteChaussette.controller;
import clapetteChaussette.model.question.QuestionLibre;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionLibreController {
    @FXML
    private Label questionFree;
    @FXML
    private TextArea textArea;
    @FXML
    private void initialize() {
        QuestionLibre qst = new QuestionLibre("rien");
        qst.fillQuestion();
        String  Affiche = qst.choiseQuestion();
        questionFree.setText(Affiche);

    }



}
