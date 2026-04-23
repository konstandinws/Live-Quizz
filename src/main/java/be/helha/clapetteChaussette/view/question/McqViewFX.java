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

    @FXML
    private CheckBox CheckBox1;

    @FXML
    private CheckBox CheckBox2;

    @FXML
    private CheckBox CheckBox3;

    @FXML
    private CheckBox CheckBox4;

    @FXML
    private void initialize() {
    }

    /**
     * Initializes the MCQ editor view by setting the question text and the four
     * answer options.
     *
     * @param answers  a list of answer labels to display in the checkboxes
     *                 (expected order: first to fourth option)
     * @param question the question text to show at the top of the editor
     */
    public McqViewFX(List<String> answers, String question) {
        questionLabel.setText(question);
        CheckBox1.setText(answers.get(0));
        CheckBox2.setText(answers.get(1));
        CheckBox3.setText(answers.get(2));
        CheckBox4.setText(answers.get(3));

    }

    @Override
    protected String getFXMLPath() {
        return "mcq.fxml";
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
        listener.validate(answer);
    }

    public interface IMcqListener extends IListener, IQuestionListener<String> {

    }
}
