package be.helha.clapetteChaussette.view.question;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.IQuestionListener;
import be.helha.clapetteChaussette.view.ViewFX;
import be.helha.clapetteChaussette.view.question.PollViewFX.IPollListener;
import javafx.event.ActionEvent;

public class PollViewFX extends ViewFX<IPollListener> {

    @FXML
    private Label title;

    @FXML
    private Button choix1;

    @FXML
    private Button choix2;

    @FXML
    private Button choix3;

    @FXML
    private Button choix4;

    public PollViewFX(String question, List<String> choices) {
        this.title.setText(question);
        this.choix1.setText(choices.get(0));
        this.choix2.setText(choices.get(1));
        this.choix3.setText(choices.get(2));
        this.choix4.setText(choices.get(3));
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        listener.validate(((Button) event.getSource()).getText());
    }

    @FXML
    private void initialize() {
    }

    @Override
    protected String getFXMLPath() {
        return "poll.fxml";
    }

    public interface IPollListener extends IListener, IQuestionListener<String> {
    }
}
