package be.helha.clapetteChaussette.view.editor;

import be.helha.clapetteChaussette.controller.IController;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class QuizzEditorFX extends ViewFX {
    private IQuizzListener controller;

    @FXML
    private Button mcqButton;

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
    void onMcqClick(ActionEvent event) {
        controller.onAddMcqRequested();

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

    @FXML
    private void initialize() {
    }

    public interface IQuizzListener extends IController {
        void onAddMcqRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested();
    }
}
