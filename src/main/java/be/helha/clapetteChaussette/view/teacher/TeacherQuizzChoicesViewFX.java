package be.helha.clapetteChaussette.view.teacher;

import be.helha.clapetteChaussette.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

    @Override
    protected String getFXMLPath() {
        return "teacherQuizzChoices.fxml";
    }

    public interface ITeacherQuizzChoicesListener extends IListener {
        void onNewQuizzRequested();
    }
}
