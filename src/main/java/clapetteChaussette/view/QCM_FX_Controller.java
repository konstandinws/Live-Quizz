package clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class QCM_FX_Controller {

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
        // Initialisation si nécessaire
    }

    @FXML
    private void CBisChecked(ActionEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        if (source.isSelected()) {
            System.out.println(source.getId() + " is checked");
        } else {
            System.out.println(source.getId() + " is unchecked");
        }
    }
}
