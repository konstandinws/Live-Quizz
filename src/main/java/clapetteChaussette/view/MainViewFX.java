package clapetteChaussette.view;

import clapetteChaussette.controller.IListenerMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainViewFX {

    IListenerMain listener;

    public void setListener(IListenerMain listener) {
        this.listener = listener;
    }

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
        listener.onQCMLaunch();
    }

    @FXML
    void launchPoll(ActionEvent event) {
        listener.onPollLaunch();
    }

    @FXML
    void launchFree(ActionEvent event) {
        listener.onFreeLaunch();
    }
}