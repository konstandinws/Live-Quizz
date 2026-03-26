package clapetteChaussette.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class mainMenuController {

    @FXML
    private Label mainLabel;

    @FXML
    private Button buttonclose;

    @FXML
    private void initialize() {
        mainLabel.setText("Hello world!");
    }


    @FXML
    private void leaveApplication(ActionEvent event) {
        Stage stage = (Stage) buttonclose.getScene().getWindow();
        stage.close();
    }
}