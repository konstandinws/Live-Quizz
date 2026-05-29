package be.helha.clapetteChaussette.client.view.student;

import be.helha.clapetteChaussette.client.controller.student.StudentWaitingController;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
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
