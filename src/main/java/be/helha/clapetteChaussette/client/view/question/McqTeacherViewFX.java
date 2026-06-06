package be.helha.clapetteChaussette.client.view.question;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.McqTeacherViewFX.ITeacherLiveListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Teacher's live view of an MCQ question: the question, its possible answers
 * (each with its live vote count), and the number of participants at the
 * bottom.
 */
public class McqTeacherViewFX extends ViewFX<ITeacherLiveListener> {

    @FXML
    private Button nextQuestionBTN;

    @FXML
    private Label questionTitle;

    @FXML
    private VBox answersVbox;

    @FXML
    private Label playersVoteCount;

    private final QuestionDTO question;
    private int participantCount;
    private int answeredCount = 0;

    /**
     * One vote-count label per answer (same order), kept to update tallies live.
     */
    private final List<Label> voteLabels = new ArrayList<>();

    public McqTeacherViewFX(QuestionDTO question, int participantCount) {
        this.question = question;
        this.participantCount = participantCount;
    }

    @FXML
    private void initialize() {
        questionTitle.setText(question.title());

        answersVbox.setSpacing(12);
        answersVbox.setPadding(new Insets(20));
        answersVbox.getChildren().clear();
        voteLabels.clear();

        for (Answer answer : question.answers()) {
            answersVbox.getChildren().add(createAnswerRow(answer.getAnswer()));
        }

        setParticipantCount(participantCount);
    }

    /** One answer row: the answer text on the left, its vote count on the right. */
    private HBox createAnswerRow(String answerText) {
        Label answerLabel = new Label(answerText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label voteLabel = new Label("0 vote");
        voteLabels.add(voteLabel);

        HBox row = new HBox(10, answerLabel, spacer, voteLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 8;"
                + " -fx-border-color: #c0c0c0; -fx-border-radius: 8;");
        return row;
    }

    @FXML
    private void onNextClick(ActionEvent event) {
        listener.onNextQuestionRequested();
    }

    /**
     * Updates the per-answer vote counts (same order as the question's answers).
     */
    public void setVotes(List<Integer> votes) {
        for (int i = 0; i < voteLabels.size() && i < votes.size(); i++) {
            int count = votes.get(i);
            voteLabels.get(i).setText(count + (count > 1 ? " votes" : " vote"));
        }
    }

    /** Updates the total number of participants (the denominator of N/N). */
    public void setParticipantCount(int count) {
        this.participantCount = count;
        refreshProgress();
    }

    /** Updates the answered/total progress shown at the bottom (e.g. "3/5"). */
    public void setVoteProgress(int answered, int total) {
        this.answeredCount = answered;
        this.participantCount = total;
        refreshProgress();
    }

    private void refreshProgress() {
        playersVoteCount.setText(answeredCount + "/" + participantCount);
    }

    @Override
    protected String getFXMLPath() {
        return "question/McqTeacher.fxml";
    }

    public interface ITeacherLiveListener extends IListener {
        void onNextQuestionRequested();
    }
}
