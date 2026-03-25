package clapetteChaussette.vue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class mainMenuController {

    @FXML
    private Label mainLabel;

    @FXML
    private void initialize() {
        mainLabel.setText("Hello world!");
    }

}