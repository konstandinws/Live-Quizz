package clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController {

    Stage primaryStage ;
    @FXML
    private Label mainLabel;

    @FXML
    private void initialize() {
        mainLabel.setText("Hello world!");
    }
    public void launchQCM(ActionEvent event) {
        Stage stage = (Stage) mainLabel.getScene().getWindow();
    }
    public void launchPoll(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/clapetteChaussette.fxml/fxmlPoll.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }
    public void launchFree(ActionEvent event) {
        Stage stage = (Stage) mainLabel.getScene().getWindow();
    }

}

    