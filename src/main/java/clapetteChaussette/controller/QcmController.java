package clapetteChaussette.controller;

import clapetteChaussette.view.IQcmView;
import clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

class QcmController implements IQcmView {
    @Override
    public void verify(CheckBox source, QcmQuestion qcm) {
        if (source.getText().equals(qcm.getCorrectAnswer())) {
            source.setTextFill(Color.GREEN);
        } else {
            source.setTextFill(Color.RED);
        }
    }
}