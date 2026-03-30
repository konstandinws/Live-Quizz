package clapetteChaussette.view;


import clapetteChaussette.controller.IListenerMain;
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

    IListenerMain listener;
    @FXML
    private Label mainLabel;

    @FXML
    private void initialize() {
        mainLabel.setText("Hello world!");
    }
    public void launchQCM(ActionEvent event) {
        Stage stage = (Stage) mainLabel.getScene().getWindow();
    }
    public void launchPoll(ActionEvent event) {listener.onPollLaunch();}

    public void launchFree(ActionEvent event) {
        Stage stage = (Stage) mainLabel.getScene().getWindow();
    }

}

    