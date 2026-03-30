package clapetteChaussette.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControleurfxmlVuePoll {
    @FXML
    private Button choix1;

    @FXML
    private Button choix2;

    @FXML
    private Button choix3;

    @FXML
    private Button choix4;


    public void handleButtonClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("Bouton cliqué !");
    }
}
