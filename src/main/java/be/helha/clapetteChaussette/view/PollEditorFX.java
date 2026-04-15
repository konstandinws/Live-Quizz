package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.model.question.Poll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PollEditorFX implements IDisplayable {
    private Poll poll;
    private IQuestionListener<String> listener;

    @FXML
    private Button addAnswerBTN;

    @FXML
    private VBox answersContainer;

    @FXML
    private TextField pollQuestion;

    @FXML
    private Button savePoll;

    @FXML
    void addNewAnswer(ActionEvent event) {

    }

    @FXML
    void onPollSave(ActionEvent event) {

    }



    @Override
    public Scene InitializeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/PollEditor.fxml"));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }



    public void setController(IQuestionListener<String> listener) {
        this.listener = listener;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;

        this.pollQuestion.setText(poll.getQuestion());
        this.choix1.setText(poll.getChoice(0));
        this.choix2.setText(poll.getChoice(1));
        this.choix3.setText(poll.getChoice(2));
        this.choix4.setText(poll.getChoice(3));
    }


    @FXML
    private void initialize() {
    }
}




