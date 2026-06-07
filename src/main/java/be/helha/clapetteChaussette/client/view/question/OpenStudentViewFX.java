package be.helha.clapetteChaussette.client.view.question;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.OpenStudentViewFX.IOpenQuestionStudentListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenStudentViewFX extends ViewFX<IOpenQuestionStudentListener> {

    private final QuestionDTO question;

    public OpenStudentViewFX(QuestionDTO question) {
        this.question = question;
    }

    @Override
    protected String getFXMLPath() {
        return "question/OpenStudent.fxml";
    }

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        questionFree.setText(question.title());
    }

    @FXML
    private void handleSendAction() {
        String answer = textArea.getText();
        if (answer.isBlank()) {
            showError("Tu dois écrire une réponse avant de soumettre.");
            return;
        }
        listener.onSubmitOpenQuestion(answer);
    }

    public interface IOpenQuestionStudentListener extends IListener {
        void onSubmitOpenQuestion(String answer);
    }
}
