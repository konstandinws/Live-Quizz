package clapetteChaussette.view;

import clapetteChaussette.model.question.QcmQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class QcmViewFX {

    private QcmQuestion qcm;
    private IQcmListener listener;

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

    public void setController(IQcmListener listener) {
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
