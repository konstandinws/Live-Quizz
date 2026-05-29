package be.helha.clapetteChaussette.client.view.ControlerComponent;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControlerComponent extends VBox {

    @FXML
    private Label labelTitre;
    @FXML
    private Label labelQuestion;
    @FXML
    private Label labelDate;

    @FXML
    private VBox root; // le nœud racine de ton composent.fxml

    // Méthode statique qui charge et retourne une instance du composant
    public static ControlerComponent creerComposent() throws IOException {
        FXMLLoader loader = new FXMLLoader(ControlerComponent.class.getResource("/Component/composent.fxml"));
        loader.load();
        ControlerComponent controller = loader.getController();
        controller.root = loader.getRoot();
        return controller;
    }

    // Retourne le nœud pour l'ajouter dans un conteneur
    public VBox getRoot() {
        return root;
    }

    public void setData(QuizzDTO question) {
        labelTitre.setText(question.name());
        labelQuestion.setText(question.name());
        root.setStyle("-fx-background-color: #f0f0f0");

    }

    @FXML
    private void initialize() {
    }
}
