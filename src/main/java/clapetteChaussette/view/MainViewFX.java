package clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainViewFX {

    private IMainListener listener;

    @FXML
    private Label mainLabel;

    @FXML
    private Button qcmButton;

    @FXML
    private Button pollButton;

    @FXML
    private Button freeButton;

    @FXML
    void launchQCM(ActionEvent event) {
        listener.onQcmLaunch();
    }

    @FXML
    void launchPoll(ActionEvent event) {
        listener.onPollLaunch();
    }

    @FXML
    void launchFree(ActionEvent event) {
        listener.onFreeLaunch();
    }

    @FXML
    private void initialize() {
        mainLabel.setText("Hello world!");
    }

    public void setController(IMainListener listener) {
        this.listener = listener;
    }
}