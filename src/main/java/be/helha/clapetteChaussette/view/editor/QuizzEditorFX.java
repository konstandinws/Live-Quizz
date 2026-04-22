package be.helha.clapetteChaussette.view.editor;

import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.view.ControlerComponent.ControlerComponent;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import be.helha.clapetteChaussette.service.QuestionService;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

public class QuizzEditorFX extends ViewFX<EditorController> {

    @FXML
    private Button mcqButton;
    private UUID teacherUuid;

    @FXML
    private Button pollButton;

    @FXML
    private Button freeButton;

    @FXML
    private Button btnSauvegarder;

    // Modification ici : le nom doit correspondre au fx:id="VboxListe" du FXML
    @FXML
    private VBox questionlistevbox;

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

    @Override
    protected String getFXMLPath() {
        return "QuizzEditor.fxml";
    }

    @FXML
    private void initialize() {
        QuestionService quizzliste = new QuestionService(new QuizzRepository());
        List<QuizzDTO> quizzes = quizzliste.getQuizzesByTeacher(teacherUuid);
        for (QuizzDTO quizz : quizzes) {
            try {
                ControlerComponent carte = ControlerComponent.creerComposent();
                carte.setData(quizz); // ← passe les données à la carte
                questionlistevbox.getChildren().add(carte.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface IQuizzListener {
        void onAddMcqRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested();
    }
}