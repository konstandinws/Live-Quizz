package clapetteChaussette.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class QuestionnaireFX {

    // l'interface pour appeler le vrai contrôleur
    private IQuestionnaireListener listener;

    // méthode pour brancher le vrai contrôleur
    public void setListener(IQuestionnaireListener listener) {
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
        if(listener != null){
            listener.onAddPollRequested();
        }
    }

    @FXML
    void onQcmClick(ActionEvent event) {
        if(listener != null){
            listener.onAddQcmRequested();
        }
    }

    @FXML
    void onQuestionFreeClick(ActionEvent event) {
        if(listener != null){
            listener.onAddQuestionFreeRequested();
        }
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        if(listener != null){
            listener.onSaveRequested();
        }
    }

}
