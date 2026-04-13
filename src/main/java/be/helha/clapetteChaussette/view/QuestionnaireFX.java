package be.helha.clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class QuestionnaireFX extends ViewFX {

    // l'interface pour appeler le vrai contrôleur
    private IQuestionnaireListener listener;

    // méthode pour brancher le vrai contrôleur
    public void setController(IQuestionnaireListener listener) {
        this.listener = listener;
    }

    @FXML
    private Button qcmButton;

    @FXML
    private Button pollButton;

    @FXML
    private Button freeButton;

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

    @Override
    protected String getFXMLPath() {
        return "questionnaire.fxml";
    }

}
