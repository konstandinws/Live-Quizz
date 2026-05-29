package be.helha.clapetteChaussette.client.view.editor;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.questions.Question;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ControlerComponent;
import be.helha.clapetteChaussette.client.view.editor.QuizzEditorFX.IQuizzListener;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class QuizzEditorFX extends ViewFX<IQuizzListener> {

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

    public void displayQuizzes(List<QuizzDTO> quizzes) {
        questionListVbox.getChildren().clear();
        for (QuizzDTO quizz : quizzes) {
            try {
                ControlerComponent carte = ControlerComponent.creerComposent();
                carte.setData(quizz);
                questionListVbox.getChildren().add(carte.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Affiche dans l'éditeur la liste des questions déjà ajoutées au quiz. */
    public void displayQuestions(List<Question> questions) {
        questionListVbox.getChildren().clear();
        for (Question question : questions) {
            try {
                ControlerComponent carte = ControlerComponent.creerComposent();
                carte.setData(question);
                questionListVbox.getChildren().add(carte.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getFXMLPath() {
        return "editor/QuizzEditor.fxml";
    }

    public interface IQuizzListener extends IListener {

        void onAddMcqRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested();
    }
}