package clapetteChaussette.view;

import clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;

public interface IQcmListener {
    public boolean verify(CheckBox source, QcmQuestion qcm);
}
