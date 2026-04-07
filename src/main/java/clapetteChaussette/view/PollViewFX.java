package clapetteChaussette.view;

import javafx.scene.control.Label;
import clapetteChaussette.model.question.Poll;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

public class PollViewFX implements IDisplayable {
    private Poll poll;
    private IQuestionListener<String> listener;

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

    @FXML
    private void handleButtonClick(ActionEvent event) {
        listener.validate(((Button) event.getSource()).getText(), poll);
    }

    public void setController(IQuestionListener<String> listener) {
        this.listener = listener;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;

        this.title.setText(poll.getQuestion());
        this.choix1.setText(poll.getChoice(0));
        this.choix2.setText(poll.getChoice(1));
        this.choix3.setText(poll.getChoice(2));
        this.choix4.setText(poll.getChoice(3));
    }

    @Override
    public Scene InitializeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/poll.fxml"));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }

    @FXML
    private void initialize() {
    }
}
