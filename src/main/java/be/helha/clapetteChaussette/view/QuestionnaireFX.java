package be.helha.clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class QuestionnaireFX implements IDisplayable {

    // l'interface pour appeler le vrai contrôleur
    private IQuestionnaireListener listener;

    // méthode pour brancher le vrai contrôleur
    public void setController(IQuestionnaireListener listener) {
        this.listener = listener;
    }

    @FXML
    private Button btnAjouterQcm;

    @FXML
    private Button btnAjouterQuestionLibre;

    @FXML
    private Button btnAjouterSondage;

    @FXML
    private Button btnSauvegarder;

    @FXML
    private VBox VboxListe;

    // méthodes pour les evenements des clics sur les boutons
    @FXML
    void onPollClick(ActionEvent event) {
        if (listener != null) {
            listener.onAddPollRequested();
        }
    }

    @FXML
    void onQcmClick(ActionEvent event) {
        if (listener != null) {
            listener.onAddQcmRequested();
        }
    }

    @FXML
    void onQuestionFreeClick(ActionEvent event) {
        if (listener != null) {
            listener.onAddQuestionFreeRequested();
        }
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        if (listener != null) {
            listener.onSaveRequested();
        }
    }

    public Scene InitializeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/questionnaire.fxml"));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }
    private void VboxListe() {
        VBox carte = new VBox(8); // 8px d'espacement entre les éléments
        carte.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 16px 20px;");
        DropShadow ombre = new DropShadow();
        ombre.setColor(Color.rgb(0, 0, 0, 0.12));
        ombre.setRadius(10);
        ombre.setOffsetY(2);
        carte.setEffect(ombre);

        // Titre en gras
        Label titre = new Label("Question sur la géographie");
        titre.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1a1a2e;" +
                        "-fx-text-color: #111111;");

        // Sous-texte
        Label question = new Label("Quelle est la capitale de la France ?");
        question.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #6b6b6b;");

        // Date avec icône
        Label date = new Label("📅 12 avril 2026");
        date.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #9e9e9e;");

        carte.getChildren().addAll(titre, question, date);
        VboxListe.getChildren().add(carte);
    }

}
