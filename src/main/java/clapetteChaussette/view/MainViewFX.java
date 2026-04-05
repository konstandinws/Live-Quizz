package clapetteChaussette.view;

import clapetteChaussette.controller.IController;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.service.QuestionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MainViewFX implements IDisplayable{

    private IMainListener listener;

    @FXML
    private Label mainLabel;

    @FXML
    private Button qcmButton;

    @FXML
    private Button pollButton;
    @FXML
    private VBox VboxListe;

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
        this.VboxListe();
    }
    private void VboxListe () {
        VBox carte = new VBox(8); // 8px d'espacement entre les éléments
        carte.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 16px 20px;"
        );
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
                        "-fx-text-color: #111111;"
        );

        // Sous-texte
        Label question = new Label("Quelle est la capitale de la France ?");
        question.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #6b6b6b;"
        );

        // Date avec icône
        Label date = new Label("📅 12 avril 2026");
        date.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #9e9e9e;"
        );

        carte.getChildren().addAll(titre, question, date);
        VboxListe.getChildren().add(carte);
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
