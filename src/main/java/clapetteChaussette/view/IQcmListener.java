package clapetteChaussette.view;

import clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;

public interface IQcmListener {
    public void verify(CheckBox source, QcmQuestion qcm);
}
