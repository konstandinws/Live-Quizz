package be.helha.clapetteChaussette.view.ControlerComponent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ControlerComponent extends VBox {

    @FXML
    private Label labelTitre;
    @FXML
    private Label labelQuestion ;
    @FXML
    private Label labelDate ;

    @FXML
    private VBox root; // le nœud racine de ton composent.fxml

    // Méthode statique qui charge et retourne une instance du composant
    public static ControlerComponent creerComposent() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                ControlerComponent.class.getResource("/Component/composent.fxml")
        );
        loader.load();
        return loader.getController();
    }

    // Retourne le nœud pour l'ajouter dans un conteneur
    public VBox getRoot() {
        return root;
    }

    // Tes méthodes métier du composant
    public void setData(Question question) {
        // ...
    }
}
