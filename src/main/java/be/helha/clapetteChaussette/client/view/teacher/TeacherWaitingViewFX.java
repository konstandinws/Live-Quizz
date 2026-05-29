package be.helha.clapetteChaussette.client.view.teacher;

import be.helha.clapetteChaussette.client.controller.teacher.TeacherWaitingController;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TeacherWaitingViewFX extends ViewFX<TeacherWaitingController> {

    @FXML
    private Label codeLabel;

    @FXML
    private Label participantNumberLabel;

    public void setCode(String code) {
        codeLabel.setText(code);
    }

    public void setParticipantCount(int count) {
        participantNumberLabel.setText(String.valueOf(count));
    }

    @Override
    protected String getFXMLPath() {
        return "teacherWaitingForQuizz.fxml";
    }

    public interface ITeacherWaitingListener extends IListener {
        void onStartQuizzRequested();
    }
}
