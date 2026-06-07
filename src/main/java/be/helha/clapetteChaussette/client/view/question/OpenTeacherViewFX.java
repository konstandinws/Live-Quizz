package be.helha.clapetteChaussette.client.view.question;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.OpenTeacherViewFX.IOpenTeacherListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpenTeacherViewFX extends ViewFX<IOpenTeacherListener> {

    @FXML
    private VBox answersVbox;

    @FXML
    private Label playersResponseCount;

    @FXML
    private Button nextQuestionBTN;

    @FXML
    private Label questionTitle;

    private QuestionDTO question;
    private int participantCount;
    private int answeredCount = 0;
    private final List<Label> answerLabels = new ArrayList<>();

    public OpenTeacherViewFX(QuestionDTO question, int participantCount) {
        this.question = question;
        this.participantCount = participantCount;
    }

    @Override
    protected String getFXMLPath() {
        return "/question/OpenTeacher.fxml";
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

    private void refreshProgress() {
        playersResponseCount.setText(answeredCount + "/" + participantCount);
    }

    /** Updates the answered/total progress shown at the bottom (e.g. "3/5"). */
    public void setVoteProgress(int answered) {
        this.answeredCount = answered;
        refreshProgress();
    }

    public void addAnswer(String answerText) {
        answersVbox.getChildren().add(createAnswerRow(answerText));
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

    public interface IOpenTeacherListener extends IListener {
        void onNextRequested();
    }

}
