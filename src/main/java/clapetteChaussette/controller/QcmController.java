package clapetteChaussette.controller;

import clapetteChaussette.view.IQcmListener;
import clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

class QcmController implements IQcmListener {

    @Override
    public boolean verify(CheckBox source, QcmQuestion qcm) {
        return source.getText().equals(qcm.getCorrectAnswer());
    }
}