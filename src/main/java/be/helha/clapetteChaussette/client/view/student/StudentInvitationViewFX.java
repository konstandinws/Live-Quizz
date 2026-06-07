package be.helha.clapetteChaussette.client.view.student;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.student.StudentInvitationViewFX.IStudentInvitationListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StudentInvitationViewFX extends ViewFX<IStudentInvitationListener> {
    @FXML
    private Button returnBTN;

    @FXML
    private TextField codeField;

    @FXML
    private TextField pseudoField;

    @FXML
    private void handleJoin() {
        try {
            listener.onJoinRequested(codeField.getText(), pseudoField.getText());
        } catch (Exception e) {
            showError("Impossible de joindre le serveur.");
        }
    }

    @Override
    protected String getFXMLPath() {
        return "studentInvitationCode.fxml";
    }

    public interface IStudentInvitationListener extends IListener {
        void onJoinRequested(String code, String pseudo) throws Exception;
    }

    @FXML
    private void initialize() {
    }
}
