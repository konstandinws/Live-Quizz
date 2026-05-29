package be.helha.clapetteChaussette.client.view.teacher;

import be.helha.clapetteChaussette.client.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class TeacherQuizzChoicesViewFX extends ViewFX<TeacherQuizzChoicesController> {

    private final String teacherName;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox quizzVbox;

    public TeacherQuizzChoicesViewFX(String teacherName) {
        this.teacherName = teacherName;
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(teacherName);
    }

    @FXML
    private void handleNewQuizz() {
        listener.onNewQuizzRequested();
    }

    /**
     * Populates the quizz list with the teacher's questionnaires.
     *
     * @param quizzes the quizzes to display (may be empty).
     */
    public void displayQuizzes(List<QuizzDTO> quizzes) {
        quizzVbox.getChildren().clear();
        if (quizzes == null || quizzes.isEmpty()) {
            quizzVbox.getChildren().add(new Label("Aucun questionnaire pour le moment."));
            return;
        }
        for (QuizzDTO quizz : quizzes) {
            quizzVbox.getChildren().add(new Label(quizz.name()));
        }
    }

    @Override
    protected String getFXMLPath() {
        return "teacherQuizzChoices.fxml";
    }

    public interface ITeacherQuizzChoicesListener extends IListener {
        void onNewQuizzRequested();
    }
}
