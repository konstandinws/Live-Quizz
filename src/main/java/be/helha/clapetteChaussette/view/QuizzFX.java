package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.IViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class QuizzFX extends ViewFX {
    private IQuestionnaireListener controller;
    // l'interface pour appeler le vrai contrôleur

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
        controller.onAddPollRequested();

    }

    @FXML
    void onQcmClick(ActionEvent event) {
        controller.onAddQcmRequested();

    }

    @FXML
    void onQuestionFreeClick(ActionEvent event) {
        controller.onAddQuestionFreeRequested();

    }

    @FXML
    void onSaveClick(ActionEvent event) {
        controller.onSaveRequested();
    }

    @Override
    protected String getFXMLPath() {
        return "QuizzEditor.fxml";
    }

    @Override
    public void setOwnViewController(IViewController controller) {
        if (controller instanceof IQuestionnaireListener) {
            this.controller = (IQuestionnaireListener) controller;
        } else {
            throw new IllegalArgumentException("Controller must implement IQuestionnaireListener");
        }
    }

    @FXML
    private void initialize() {
        // Hint: initialize() will be called when the associated FXML has been
        // completely loaded.
    }

    public interface IQuestionnaireListener extends IViewController {
        void onAddQcmRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested(); // quand l'éditeur a fini d'ajouter des questions.

    }
}
