package be.helha.clapetteChaussette.client.view.question;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpenTeacherViewFX extends TeacherQuestionViewFX {

    @FXML
    private VBox answersVbox;

    @FXML
    private Label playersResponseCount;

    @FXML
    private Button nextQuestionBTN;

    @FXML
    private Label questionTitle;

    private final List<Label> answerLabels = new ArrayList<>();

    public OpenTeacherViewFX(QuestionDTO question, int participantCount) {
        super(question, participantCount);
    }

    @Override
    protected String getFXMLPath() {
        return "question/OpenTeacher.fxml";
    }

    private HBox createAnswerRow(String answerText) {
        Label answerLabel = new Label(answerText);

        answerLabels.add(answerLabel);

        HBox row = new HBox(10, answerLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 8;"
                + " -fx-border-color: #c0c0c0; -fx-border-radius: 8;");
        return row;
    }

    protected void refreshProgress() {
        playersResponseCount.setText(answeredCount + "/" + participantCount);
    }

    public void addAnswer(List<String> answerText) {
        answersVbox.getChildren().clear();
        for (String text : answerText) {
            answersVbox.getChildren().add(createAnswerRow(text));
        }
    }

    @Override
    public void setVotes(List<Integer> votes) {
        return;
    }

    @FXML
    private void onNextClick(ActionEvent event) {
        listener.onNextRequested();
    }

    @FXML
    private void initialize() {
        questionTitle.setText(question.title());

        answersVbox.setSpacing(12);
        answersVbox.setPadding(new Insets(20));
        answersVbox.getChildren().clear();
        answerLabels.clear();
    }
}
