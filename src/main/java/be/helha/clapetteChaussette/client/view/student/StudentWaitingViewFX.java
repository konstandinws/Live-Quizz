package be.helha.clapetteChaussette.client.view.student;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.student.StudentWaitingViewFX.IStudentWaitingListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentWaitingViewFX extends ViewFX<IStudentWaitingListener> {
    @FXML
    private Button returnBTN;

    @FXML
    private Label participantNumberLabel;

    public void setParticipantCount(int count) {
        participantNumberLabel.setText(String.valueOf(count));
    }

    /** Overrides the default "Retour" action (e.g. to leave the session first). */
    public void setOnReturn(Runnable action) {
        if (returnBTN != null) {
            returnBTN.setOnAction(e -> action.run());
        }
    }

    @Override
    protected String getFXMLPath() {
        return "studentWaintingForQuizz.fxml";
    }

    public interface IStudentWaitingListener extends IListener {
        void onParticipantCountUpdated(int count);
    }
}
