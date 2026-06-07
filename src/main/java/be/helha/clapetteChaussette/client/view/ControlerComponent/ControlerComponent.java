package be.helha.clapetteChaussette.client.view.ControlerComponent;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.questions.Question;
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

    // Le composant EST son propre nœud racine (pattern <fx:root>).
    public ControlerComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                ControlerComponent.class.getResource("/clapetteChaussette/fxml/Component/composent.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    // Méthode statique qui charge et retourne une instance du composant
    public static ControlerComponent creerComposent() throws IOException {
        return new ControlerComponent();
    }

    // Retourne le nœud pour l'ajouter dans un conteneur
    public VBox getRoot() {
        return this;
    }

    public void setData(QuizzDTO question) {
        labelTitre.setText(question.name());
        labelQuestion.setText(question.name());

    }

    // Affiche une question de l'éditeur : son intitulé et son type (QCM, POLL, OPEN).
    public void setData(Question question) {
        labelTitre.setText(question.getQuestion());
        labelQuestion.setText(question.getType().toString());
    }

    @FXML
    private void initialize() {
    }
}
