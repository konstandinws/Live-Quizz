package be.helha.clapetteChaussette.view.editor;

import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class QuizzEditorFX extends ViewFX<EditorController> {

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
        listener.onAddPollRequested();

    }

    @FXML
    void onMcqClick(ActionEvent event) {
        listener.onAddMcqRequested();

    }

    @FXML
    void onQuestionFreeClick(ActionEvent event) {
        listener.onAddQuestionFreeRequested();

    }

    @FXML
    void onSaveClick(ActionEvent event) {
        listener.onSaveRequested();
    }

    @Override
    protected String getFXMLPath() {
        return "QuizzEditor.fxml";
    }

    @FXML
    private void initialize() {
    }

    public interface IQuizzListener {
        void onAddMcqRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested();
    }
}
