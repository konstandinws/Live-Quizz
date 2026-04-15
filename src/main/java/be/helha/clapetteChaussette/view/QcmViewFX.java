package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.model.question.QcmQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class QcmViewFX extends ViewFX {

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
        CheckBox1.setText(qcm.getAnswers().get(0).getAnwser());
        CheckBox2.setText(qcm.getAnswers().get(1).getAnwser());
        CheckBox3.setText(qcm.getAnswers().get(2).getAnwser());
        CheckBox4.setText(qcm.getAnswers().get(3).getAnwser());

        laQuestion.setText(qcm.getQuestion());
    }

    @Override
    protected String getFXMLPath() {
        return "qcm.fxml";
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

    @Override
    public void setOwnViewController(IViewController controller) {
        if (controller instanceof IQuestionListener) {
            this.listener = (IQuestionListener<String>) controller;
        } else {
            throw new IllegalArgumentException("Controller must implement IQuestionListener<String>");
        }
    }

    public interface IQcmListener extends IQuestionListener<String> {
        public boolean verify(CheckBox source, QcmQuestion qcm);
    }
}
