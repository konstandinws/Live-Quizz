package clapetteChaussette.view;

import javafx.scene.control.Label;

import clapetteChaussette.model.question.Poll;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

public class PollViewFX {
    private Poll poll;
    private IPoolListener listener;

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
    private void choix1Action(ActionEvent event) {

    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Button clicked: " + event.getSource());
    }

    public void set(IPoolListener listener, Poll poll) {
        this.listener = listener;
        this.poll = poll;

        this.title.setText(poll.getQuestion());
        this.choix1.setText(poll.getChoice(0));
        this.choix2.setText(poll.getChoice(1));
        this.choix3.setText(poll.getChoice(2));
        this.choix4.setText(poll.getChoice(3));
    }
}
