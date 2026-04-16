package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.ControlerComponent.ControlerComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class QuizzFX extends ViewFX {
    private IQuestionnaireListener controller;
    // l'interface pour appeler le vrai contrôleur

    private ControlerComponent controlerComponent;

    @FXML
    private Button qcmButton;

    @FXML
    private Button pollButton;

    @FXML
    private Button freeButton;

    @FXML
    private Button btnSauvegarder;

    @FXML
    private VBox VboxListe;

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
        this.chargerQuestions();
    }

    public interface IQuestionnaireListener extends IViewController {
        void onAddQcmRequested();

        void onAddPollRequested();

        void onAddQuestionFreeRequested();

        void onSaveRequested(); // quand l'éditeur a fini d'ajouter des questions.

    }


    public void chargerQuestions(List<Question> questions) throws IOException {
        for (Question q : questions) {

            ControlerComponent composant = ControlerComponent.creerComposent();
            composant.setData(q);
            VboxListe.getChildren().add(composant.getRoot());

        }
    }
}
