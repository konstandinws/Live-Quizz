package clapetteChaussette.view;

import clapetteChaussette.model.question.OpenQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenQuestionFX {

    @FXML
    private Label questionFree; // Assure-toi que l'ID dans le FXML est bien "questionFree"

    @FXML
    private TextArea textArea; // Assure-toi que l'ID dans le FXML est bien "textArea"

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

    /**
     * CETTE MÉTHODE DOIT AVOIR LE MÊME NOM QUE DANS LE FXML
     * Dans ton cas : handleSendAction
     */
    @FXML
    private void handleSendAction() {
        if (open != null && textArea != null) {
            listener.validate(textArea.getText(), open);
        }
    }
}