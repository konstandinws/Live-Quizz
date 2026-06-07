package be.helha.clapetteChaussette.client.view.question;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Student's live view of an MCQ question: the question, selectable answers, and
 * a "Valider" button to submit the chosen answer.
 */
public class McqStudentViewFX extends StudentQuestionViewFX {

    @FXML
    private Label questionTitle;

    @FXML
    private VBox answersVbox;

    @FXML
    private Button validateButton;

    private final ToggleGroup group = new ToggleGroup();
    private final List<RadioButton> options = new ArrayList<>();
    private int selectedIndex = -1;
    private boolean revealed = false;

    public McqStudentViewFX(QuestionDTO question) {
        super(question);
    }

    @FXML
    private void initialize() {
        questionTitle.setText(question.title());

        answersVbox.getChildren().clear();
        options.clear();
        for (Answer answer : question.answers()) {
            RadioButton option = new RadioButton(answer.getAnswer());
            option.setToggleGroup(group);
            options.add(option);
            answersVbox.getChildren().add(option);
        }
    }

    @FXML
    private void onValidate(ActionEvent event) {
        int index = options.indexOf((RadioButton) group.getSelectedToggle());
        if (index < 0) {
            showError("Choisis une réponse avant de valider.");
            return;
        }
        selectedIndex = index;
        listener.onAnswerSelected(index);
    }

    /** Locks the answer in: once submitted, the student can no longer change it. */
    public void lockAfterAnswer() {
        options.forEach(option -> option.setDisable(true));
        validateButton.setDisable(true);
        validateButton.setText("Réponse envoyée");
    }

    /**
     * Reveals the result when the teacher shows the answers: correct options turn
     * green, a wrong pick turns red, and a label says if the student got it right.
     */
    @Override
    public void revealCorrectAnswers(QuestionDTO correctQuestion, List<Integer> votes) {
        if (revealed) {
            return;
        }
        revealed = true;
        List<Answer> answers = correctQuestion.answers();
        for (int i = 0; i < options.size() && i < answers.size(); i++) {
            RadioButton option = options.get(i);
            option.setDisable(false);
            option.setMouseTransparent(true);
            if (answers.get(i).isCorrect()) {
                option.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            } else if (i == selectedIndex) {
                option.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
            }
        }

        boolean correct = selectedIndex >= 0 && answers.get(selectedIndex).isCorrect();
        Label feedback = new Label(correct ? "Bonne réponse !" : "Mauvaise réponse");
        feedback.setStyle("-fx-font-weight: bold; -fx-font-size: 16;"
                + " -fx-text-fill: " + (correct ? "#2e7d32" : "#c62828") + ";");
        answersVbox.getChildren().add(feedback);
    }

    @Override
    protected String getFXMLPath() {
        return "question/McqStudent.fxml";
    }
}
