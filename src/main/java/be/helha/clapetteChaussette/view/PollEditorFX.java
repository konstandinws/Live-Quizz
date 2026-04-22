package be.helha.clapetteChaussette.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.controller.editor.EditorController;


/**
 * JavaFX controller for the Poll editing interface.
 * Inherits from ViewFX based on the new project architecture.
 */
public class PollEditorFX extends ViewFX<EditorController> {

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
            showErrorPopup(e.getMessage());
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
                showErrorPopup("Vous devez avoir au minimum 2 options pour un sondage.");
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

    private void showErrorPopup(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Impossible de sauvegarder le sondage");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mandatory method inherited from ViewFX.
     */
    @Override
    protected String getFXMLPath() {
        return "PollEditor.fxml";
    }

    public interface IPollEditorListener {
        void createPoll(String title, List<String> answers) throws InvalidQuestionException;

    }
}