package be.helha.clapetteChaussette.client.view.student;

import be.helha.clapetteChaussette.client.controller.student.StudentInvitationController;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StudentInvitationViewFX extends ViewFX<StudentInvitationController> {

    @FXML
    private TextField codeField;

    @FXML
    private void handleJoin() {
        listener.onJoinRequested(codeField.getText());
    }

    @Override
    protected String getFXMLPath() {
        return "studentInvitationCode.fxml";
    }

    public interface IStudentInvitationListener extends IListener {
        void onJoinRequested(String code);
    }
}
