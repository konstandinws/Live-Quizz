package be.helha.clapetteChaussette.view.ControlerComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControlerComponent {

    @FXML
    private Label labelTitre;
    @FXML
    private Label labelQuestion ;
    @FXML
    private Label labelDate ;

    protected void initialize() {
        labelTitre.setText("Titre");
        labelQuestion.setText("Question");
        labelDate.setText("Date");

    }
}
