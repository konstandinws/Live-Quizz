package be.helha.clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
    private VBox listeQuestionsBox;

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

}
