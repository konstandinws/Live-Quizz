package be.helha.clapetteChaussette.view.question;

import java.util.List;

import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.IQuestionListener;
import be.helha.clapetteChaussette.view.ViewFX;
import be.helha.clapetteChaussette.view.question.McqViewFX.IMcqListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class McqViewFX extends ViewFX<IMcqListener> {

    @FXML
    private Label questionLabel;

    private String question;
    private List<String> answers;

    private List<CheckBox> answersCheckBoxs;

    /**
     * Initializes the MCQ editor view by setting the question text and the four
     * answer options.
     *
     * @param answers  a list of answer labels to display in the checkboxes
     *                 ed order: first to fourth option)
     * @param question stion text to show at the top of the editor
     */
    public McqViewFX(List<String> answers, String question) {
        this.answers = answers;
        this.question = question;
    }

    @FXML
    private void initialize() {
    }

    @Override
    protected String getFXMLPath() {
        return "question/mcq.fxml";
    }

    @FXML
    private void CBisChecked(ActionEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        if (source.isSelected()) {
            verify(source.getText());
        } else {
            source.setTextFill(Color.BLACK);
        }

    }

    private void verify(String answer) {
    }

    public interface IMcqListener extends IListener, IQuestionListener<Integer> {

    }
}
