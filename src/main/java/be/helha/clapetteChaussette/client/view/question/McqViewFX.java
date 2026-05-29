package be.helha.clapetteChaussette.client.view.question;

import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.IQuestionView;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.McqViewFX.IMcqListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class McqViewFX extends ViewFX<IMcqListener> implements IQuestionView {
    @FXML
    private Label questionLabel;

    @FXML
    private VBox AnwserContainer;
    private List<CheckBox> answersCheckBoxs;

    private String question;
    private List<String> answers;
    private boolean asAlreadySent = false;
    private int selectedIndex = -1;

    /**
     * Initializes the MCQ editor view by setting the question text and the four
     * answer options.
     *
     * @param answers  a list of answer labels to display in the checkboxes
     *                 ed order: first to fourth option)
     * @param question stion text to show at the top of the editor
     */
    public McqViewFX(List<String> answers, String question) {
        this.answers = answers;
        this.question = question;
    }

    @FXML
    private void initialize() {
        questionLabel.setText(question);
        int counter = 0;
        for (String answer : answers) {
            CheckBox checkBox = new CheckBox(); // Create a new CheckBox for each answer
            checkBox.setText(answer);
            checkBox.setOnAction(event -> send(event)); // we use a lambda who is anonymous function to call the send
                                                        // method when the CheckBox is clicked. Here we just pass the
                                                        // ActionEvent to the send method, which can be used to
                                                        // determine which CheckBox was clicked and perform the
                                                        // appropriate action based on that.

            HBox hBox; // Determine which HBox to add the CheckBox to based on the counter
            if (counter < 2) {
                hBox = (HBox) AnwserContainer.getChildren().get(0);
            } else if (counter < 4) {
                hBox = (HBox) AnwserContainer.getChildren().get(1);
            } else if (counter < 6) {
                hBox = (HBox) AnwserContainer.getChildren().get(2);
            } else {
                throw new IllegalStateException("Too many answers provided for MCQ view");
            }

            hBox.getChildren().add(checkBox); // Add the CheckBox to the appropriate HBox
            answersCheckBoxs.add(checkBox);
            counter++;
        }
    }

    @Override
    protected String getFXMLPath() {
        return "question/mcq.fxml";
    }

    private void send(ActionEvent event) {
        if (!asAlreadySent) {
            asAlreadySent = true;

            CheckBox sourceCheckBox = (CheckBox) event.getSource();
            int selectedIndex = answersCheckBoxs.indexOf(sourceCheckBox);
            if (selectedIndex != -1) {
                listener.onMcqAnswerSelected(selectedIndex);
            }
            this.selectedIndex = selectedIndex;
        }
    }

    @Override
    public void showWaiting() {
        answersCheckBoxs.forEach(checkBox -> checkBox.setDisable(true));
    }

    @Override
    public void showCorrect() {
        if (selectedIndex != -1) {
            answersCheckBoxs.get(selectedIndex).setStyle("-fx-background-color: green;");
        }
    }

    @Override
    public void showIncorrect() {
        if (selectedIndex != -1) {
            answersCheckBoxs.get(selectedIndex).setStyle("-fx-background-color: red;");
        }
    };

    public interface IMcqListener extends IListener {
        void onMcqAnswerSelected(int index);
    }
}
