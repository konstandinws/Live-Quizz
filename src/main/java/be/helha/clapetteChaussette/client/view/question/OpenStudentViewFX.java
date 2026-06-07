package be.helha.clapetteChaussette.client.view.question;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class OpenStudentViewFX extends StudentQuestionViewFX {

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitButton;

    private String submittedAnswer = "";
    private boolean revealed = false;

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
        submittedAnswer = answer;
        listener.onAnswerSelected(answer);
    }

    @Override
    public void lockAfterAnswer() {
        textArea.setDisable(true);
        submitButton.setDisable(true);
    }

    /**
     * Reveals the result when the teacher shows the answers: tells the student if
     * the text they submitted matches one of the expected answers.
     */
    @Override
    public void revealCorrectAnswers(QuestionDTO correctQuestion, List<Integer> votes) {
        if (revealed) {
            return;
        }
        revealed = true;
        boolean correct = isAnswerCorrect(submittedAnswer, correctQuestion);
        String message = correct
                ? "Bonne réponse !"
                : "Mauvaise réponse — réponse(s) attendue(s) : " + expectedAnswersText(correctQuestion);

        Label feedback = new Label(message);
        feedback.setWrapText(true);
        feedback.setStyle("-fx-font-weight: bold; -fx-font-size: 14;"
                + " -fx-text-fill: " + (correct ? "#2e7d32" : "#c62828") + ";");
        ((VBox) textArea.getParent()).getChildren().add(feedback);
    }

    /** A text answer is correct if it matches one of the expected answers. */
    private boolean isAnswerCorrect(String answer, QuestionDTO correctQuestion) {
        String normalized = answer.trim();
        for (Answer expected : correctQuestion.answers()) {
            if (expected.getAnswer().trim().equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }

    private String expectedAnswersText(QuestionDTO correctQuestion) {
        List<String> texts = new ArrayList<>();
        for (Answer expected : correctQuestion.answers()) {
            texts.add(expected.getAnswer());
        }
        return String.join(", ", texts);
    }
}
