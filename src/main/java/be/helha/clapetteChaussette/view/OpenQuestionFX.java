package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.model.question.OpenQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenQuestionFX extends ViewFX {
    @FXML
    private Button envoyerBtn;

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    private IQuestionListener<String> listener;
    private OpenQuestion open;

    @FXML
    private void initialize() {
        // On ne crée plus la question ici, on attend qu'elle vienne du MainController
    }

    /**
     * Cette méthode permet au MainController de passer les données à la vue
     */
    public void setController(IQuestionListener<String> listener) {
        this.listener = listener;
    }

    public void setOpenQuestion(OpenQuestion question) {
        this.open = question;
        if (question != null && questionFree != null) {
            this.questionFree.setText(question.getQuestion());
        }
    }

    @FXML
    private void handleSendAction() {
        if (open != null && textArea != null) {
            listener.validate(textArea.getText(), open);
        }
    }

    @Override
    protected String getFXMLPath() {
        return "open.fxml";
    }
}
