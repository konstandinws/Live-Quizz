package clapetteChaussette.view;

import clapetteChaussette.controller.IListenerMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainViewFX {

    public interface Listener {
        void onQcmLaunch();
    }

    private Listener listener;

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

    }

    @FXML
    void launchFree(ActionEvent event) {

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}