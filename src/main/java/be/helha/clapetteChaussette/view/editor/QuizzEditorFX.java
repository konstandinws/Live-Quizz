package be.helha.clapetteChaussette.view.editor;

import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.repository.IQuestionRepository;
import be.helha.clapetteChaussette.repository.IQuizzRepository;
import be.helha.clapetteChaussette.view.ControlerComponent.ControlerComponent;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

public class QuizzEditorFX extends ViewFX<EditorController> {
    private IQuizzRepository dbService;
    private UUID teacherUuid;

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
    private VBox questionListVbox;

    // Modification ici : on renomme pour correspondre au onAction="#onQcmClick" du
    // FXML
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

    @FXML
    private void initialize() {
        List<QuizzDTO> quizzes = dbService.findAll(teacherUuid);
        for (QuizzDTO quizz : quizzes) {
            try {
                ControlerComponent carte = ControlerComponent.creerComposent();
                carte.setData(quizz); // ← passe les données à la carte
                questionListVbox.getChildren().add(carte.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getFXMLPath() {
        return "QuizzEditor.fxml";
    }

    public void setDbService(IQuizzRepository quizzRepository) {
        this.dbService = quizzRepository;
    }

    public interface IQuizzListener {
        void onAddMcqRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested();
    }
}