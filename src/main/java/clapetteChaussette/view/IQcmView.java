package clapetteChaussette.view;

import clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;

public interface IQcmView {
    public void verify(CheckBox source, QcmQuestion qcm);
}
