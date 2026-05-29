package be.helha.clapetteChaussette.client.view.question;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.IQuestionView;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.PollViewFX.IPollListener;
import javafx.event.ActionEvent;

public class PollViewFX extends ViewFX<IPollListener> implements IQuestionView {

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
        Button clickedButton = (Button) event.getSource();
        listener.onPollAnswerSelected(clickedButton.getText());
    }

    @FXML
    private void initialize() {
    }

    @Override
    protected String getFXMLPath() {
        return "question/poll.fxml";
    }

    @Override
    public void showWaiting() {
        choix1.setDisable(true);
        choix2.setDisable(true);
        choix3.setDisable(true);
        choix4.setDisable(true);
    }

    @Override // this method should be nothing because the poll question is not about being
              // correct or incorrect
    public void showCorrect() {
    };

    @Override
    public void showIncorrect() {
    };

    public interface IPollListener extends IListener {
        void onPollAnswerSelected(String answer);
    }
}
