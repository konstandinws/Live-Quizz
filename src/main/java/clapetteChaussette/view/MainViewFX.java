package clapetteChaussette.view;

import clapetteChaussette.controller.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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
    private void initialize() {mainLabel.setText("Clapette Chaussette");
        mainLabel.setStyle(
                "-fx-background-color: #fff;" +
                        "-fx-text-fill: purple;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 5px 10px;"
        );
    }

    public void setController(IMainListener listener) {
        this.listener = listener;
    }

    @Override
    public Scene InitializeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/clapetteChaussette.fxml"));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }
}
