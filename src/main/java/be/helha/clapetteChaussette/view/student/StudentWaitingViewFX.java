package be.helha.clapetteChaussette.view.student;

import be.helha.clapetteChaussette.controller.student.StudentWaitingController;
import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentWaitingViewFX extends ViewFX<StudentWaitingController> {

    @FXML
    private Label participantNumberLabel;

    public void setParticipantCount(int count) {
        participantNumberLabel.setText(String.valueOf(count));
    }

    @Override
    protected String getFXMLPath() {
        return "studentWaintingForQuizz.fxml";
    }

    public interface IStudentWaitingListener extends IListener {
        void onParticipantCountUpdated(int count);
    }
}
