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

public class PollStudentViewFX extends StudentQuestionViewFX {

    @FXML
    private Label questionTitle;

    @FXML
    private VBox answersVbox;

    @FXML
    private Button validateButton;

    private final ToggleGroup group = new ToggleGroup();
    private final List<RadioButton> options = new ArrayList<>();

    public PollStudentViewFX(QuestionDTO question) {
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
        listener.onAnswerSelected(index);
    }

    /** Locks the answer in: once submitted, the student can no longer change it. */
    public void lockAfterAnswer() {
        options.forEach(option -> option.setDisable(true));
        validateButton.setDisable(true);
        validateButton.setText("Réponse envoyée");
    }

    @Override
    protected String getFXMLPath() {
        return "question/PollStudent.fxml";
    }
}
