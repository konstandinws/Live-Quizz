package be.helha.clapetteChaussette.client.view.editor;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.editor.PollEditorFX.IPollEditorListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;

/**
 * JavaFX controller for the Poll editing interface.
 * Inherits from ViewFX based on the new project architecture.
 */
public class PollEditorFX extends ViewFX<IPollEditorListener> {

    @FXML
    private VBox answersContainer;

    @FXML
    private TextField pollQuestion;

    @FXML
    private Button addAnswerBTN;

    @FXML
    private Button savePoll;

    final int MAX_ANSWERS = 6;
    final int MIN_ANSWERS = 2;

    private List<TextField> textFields = new ArrayList<>();

    /**
     * Initializes the view with 2 default answer fields.
     */
    @FXML
    public void initialize() {
        for (int i = 0; i < MIN_ANSWERS; i++) {
            answersContainer.getChildren().add(createAnswerRow());
        }
    }

    /**
     * Linked to the "+" button in the FXML.
     */
    @FXML
    void addNewAnswer() {
        answersContainer.getChildren().add(createAnswerRow());
        setAddAnswerBTNVisibility();
    }

    boolean checkMaxAnswers() {
        return answersContainer.getChildren().size() >= MAX_ANSWERS;
    }

    void setAddAnswerBTNVisibility() {
        addAnswerBTN.setDisable(checkMaxAnswers());
    }

    /**
     * Linked to the "Save" button in the FXML.
     */
    @FXML
    void onPollSave() {
        try {
            String title = pollQuestion.getText();
            List<String> answers = getAnswers();

            listener.createPoll(title, answers);

        } catch (InvalidQuestionException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Creates a row with a text field and a "Delete" button.
     */
    private HBox createAnswerRow() {
        HBox answerRow = new HBox();
        answerRow.setAlignment(Pos.CENTER_LEFT);
        answerRow.setPadding(new Insets(5, 10, 5, 10));

        TextField answerTF = new TextField();
        answerTF.setPromptText("Option de réponse");
        HBox.setHgrow(answerTF, Priority.ALWAYS);

        Button deleteBTN = new Button("Supprimer");
        textFields.add(answerTF);

        deleteBTN.setOnAction(e -> {
            if (answersContainer.getChildren().size() > MIN_ANSWERS) {
                answersContainer.getChildren().remove(answerRow);
                textFields.remove(answerTF);
                setAddAnswerBTNVisibility();
            } else {
                showError("Vous devez avoir au minimum 2 options pour un sondage.");
            }
        });

        answerRow.getChildren().addAll(answerTF, deleteBTN);
        return answerRow;
    }

    List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        for (TextField field : textFields) {
            if (!field.getText().trim().isEmpty()) {
                answers.add(field.getText());
            }
        }
        return answers;
    }

    /**
     * Mandatory method inherited from ViewFX.
     */
    @Override
    protected String getFXMLPath() {
        return "editor/PollEditor.fxml";
    }

    public interface IPollEditorListener extends IListener {
        void createPoll(String title, List<String> answers) throws InvalidQuestionException;

    }
}