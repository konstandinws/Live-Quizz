package clapetteChaussette.view;

import clapetteChaussette.controller.IController;
import clapetteChaussette.model.question.QcmQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

public class QcmViewFX implements IDisplayable {

    private QcmQuestion qcm;
    private IQuestionListener<String> listener;

    @FXML
    private Label laQuestion;

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

    public void setController(IQuestionListener<String> listener) {
        this.listener = listener;
    }

    public void setQcm(QcmQuestion qcm) {
        this.qcm = qcm;
        CheckBox1.setText(qcm.getAnswers().get(0));
        CheckBox2.setText(qcm.getAnswers().get(1));
        CheckBox3.setText(qcm.getAnswers().get(2));
        CheckBox4.setText(qcm.getAnswers().get(3));

        laQuestion.setText(qcm.getQuestion());
    }

    public Scene InitializeView(IController controller) throws Exception {
        Parent root = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/qcm.fxml")).load();
        if (controller instanceof IQuestionListener) {
            setController((IQuestionListener<String>) controller);
        } else {
            throw new IllegalArgumentException("Controller must implement IQuestionListener<String>");
        }
        return new Scene(root);
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
        listener.validate(answer, qcm);
    }
}
