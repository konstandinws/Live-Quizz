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

    // Modification ici : le nom doit correspondre au fx:id="VboxListe" du FXML
    @FXML
    private VBox VboxListe;

    // Modification ici : on renomme pour correspondre au onAction="#onQcmClick" du FXML
    @FXML
    void onQcmClick(ActionEvent event) {
        listener.onAddMcqRequested();
    }

    @FXML
    void onPollClick(ActionEvent event) {
        listener.onAddPollRequested();
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
        // Cette méthode sera appelée une fois le FXML chargé.
    }

    public interface IQuizzListener {
        void onAddMcqRequested();
        void onAddPollRequested();
        void onAddQuestionFreeRequested();
        void onSaveRequested();
    }
}