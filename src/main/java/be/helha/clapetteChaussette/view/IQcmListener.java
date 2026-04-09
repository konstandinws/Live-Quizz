package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.model.question.QcmQuestion;
import javafx.scene.control.CheckBox;

public interface IQcmListener {
    public boolean verify(CheckBox source, QcmQuestion qcm);
}
