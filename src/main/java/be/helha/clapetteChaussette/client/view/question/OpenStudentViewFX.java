package be.helha.clapetteChaussette.client.view.question;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenStudentViewFX extends StudentQuestionViewFX {

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitButton;

    public OpenStudentViewFX(QuestionDTO question) {
        super(question);
    }

    @Override
    protected String getFXMLPath() {
        return "question/OpenStudent.fxml";
    }

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
        listener.onAnswerSelected(answer);
    }

    @Override
    public void lockAfterAnswer() {
        textArea.setDisable(true);
        submitButton.setDisable(true);
    }
}
