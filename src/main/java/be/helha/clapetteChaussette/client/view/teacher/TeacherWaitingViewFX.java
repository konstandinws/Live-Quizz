package be.helha.clapetteChaussette.client.view.teacher;

import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TeacherWaitingViewFX extends ViewFX<ITeacherWaitingListener> {
    @FXML
    private Button launchButton;

    @FXML
    private Button returnBTN;

    @FXML
    private VBox nameVBox;

    @FXML
    private Label codeLabel;

    @FXML
    private Label participantNumberLabel;

    public void setCode(String code) {
        codeLabel.setText(code);
    }

    public void setParticipantNames(List<String> names) {
        nameVBox.getChildren().clear();
        String message = "Nouveaux participants" + "Les participants suivants ont rejoint la session :\n"
                + String.join("\n", names);
        ErrorPopup.show(message);
        for (String name : names) {
            Label nameLabel = new Label(name);
            nameVBox.getChildren().add(nameLabel);
        }
    }

    public void setParticipantCount(int count) {
        participantNumberLabel.setText(String.valueOf(count));
    }

    public void onLaunch() {
        listener.onStartQuizzRequested();
    }

    @Override
    protected String getFXMLPath() {
        return "teacherWaitingForQuizz.fxml";
    }

    public interface ITeacherWaitingListener extends IListener {
        void onStartQuizzRequested();
    }

    @FXML
    private void initialize() {
    }
}
