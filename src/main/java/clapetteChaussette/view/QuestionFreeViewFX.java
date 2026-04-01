package clapetteChaussette.view;

import clapetteChaussette.model.question.QuestionFree;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionFreeViewFX {

    @FXML
    private Label questionFree; // Assure-toi que l'ID dans le FXML est bien "questionFree"

    @FXML
    private TextArea textArea; // Assure-toi que l'ID dans le FXML est bien "textArea"

    private IFreeListener listener;

    @FXML
    private void initialize() {
        // On ne crée plus la question ici, on attend qu'elle vienne du MainController
    }

    /**
     * Cette méthode permet au MainController de passer les données à la vue
     */
    public void set(IFreeListener listener, QuestionFree question) {
        this.listener = listener;
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
        if (listener != null && textArea != null) {
            listener.validate(textArea.getText());
        }
    }
}