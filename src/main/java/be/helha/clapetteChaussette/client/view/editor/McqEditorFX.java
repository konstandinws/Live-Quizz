package be.helha.clapetteChaussette.client.view.editor;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.editor.McqEditorFX.IMcqEditorListener;

/**
 * JavaFX controller for the Multiple Choice Question (MCQ) editing interface.
 * <p>
 * Manages the dynamic display of answer rows and collects user input
 * before passing the data to {@link QcmEditorController}.
 * Business validation is delegated to the main controller; this class
 * only handles visual validation (error display).
 */
public class McqEditorFX extends ViewFX<IMcqEditorListener> {
    @FXML
    private Button saveQuestionBTN;

    @FXML
    private VBox answersContainer;
    @FXML
    private TextField questionTitle;
    @FXML
    private Button addAnswerBTN;

    /** The maximum number of answers allowed for an MCQ. */
    final int MAX_ANSWERS = 6;
    /** The minimum number of answers required for an MCQ. */
    final int MIN_ANSWERS = 2;

    private List<CheckBox> correctAnswerCB = new ArrayList<>();
    private List<TextField> textFields = new ArrayList<>();

    /**
     * Méthode appelée automatiquement par JavaFX après le chargement du fichier
     * FXML.
     * Initialise la vue avec 4 champs de réponses par défaut.
     */
    @FXML
    public void initialize() {
        for (int i = 0; i < 4; i++) {
            answersContainer.getChildren().add(createAnswerRow());
        }
    }

    /**
     * Adds a new answer row to {@code answersContainer}.
     * Disables the add button if the maximum number of answers is reached.
     * Linked to the "+" button in the FXML.
     */
    @FXML
    private void addNewAnswer() {
        answersContainer.getChildren().add(createAnswerRow());
        setAddAnswerBTNVisibility();
    }

    /**
     * Checks if the maximum number of allowed answers has been reached.
     *
     * @return {@code true} if {@code answersContainer} contains
     *         {@code MAX_ANSWERS} or more answers.
     */
    boolean checkMaxAnswers() {
        return answersContainer.getChildren().size() >= MAX_ANSWERS;
    }

    /**
     * Enables or disables the add answer button based on
     * {@link #checkMaxAnswers()}.
     */
    void setAddAnswerBTNVisibility() {
        addAnswerBTN.setDisable(checkMaxAnswers());
    }

    /**
     * Collects data from the view and requests the controller to create the
     * question.
     * Displays an error popup if validation fails.
     * Linked to the "Save" button in the FXML.
     */
    @FXML
    private void createQuestion() {
        String title = getTitle();
        List<String> answers = textFields.stream().map(TextField::getText).toList();
        List<Boolean> correctAnswers = correctAnswerCB.stream()
                .map(CheckBox::isSelected)
                .toList();
        try {
            listener.createMcqQuestion(title, answers, correctAnswers);
        } catch (InvalidQuestionException e) {
            showErrorPopup(e.getMessage());
        }
    }

    /**
     * Creates and configures a row representing a possible answer for the MCQ.
     * <p>
     * The row contains a {@link CheckBox} to mark the answer as correct,
     * a {@link TextField} for the text input, and a "Delete" button.
     * <p>
     * The {@link CheckBox} and {@link TextField} are automatically added
     * to the internal {@code correctAnswerCB} and {@code textFields} lists.
     * <p>
     * Deletion is blocked and a popup is displayed if the number of answers
     * is less than or equal to {@code MIN_ANSWERS}.
     *
     * @return an {@link HBox} ready to be added to {@code answersContainer}
     */
    private HBox createAnswerRow() {
        HBox answerRow = new HBox();
        answerRow.setAlignment(Pos.CENTER_LEFT);
        answerRow.setPadding(new Insets(5, 10, 5, 10));

        CheckBox validAnswerCB = new CheckBox();

        TextField answerTF = new TextField();
        HBox.setHgrow(answerTF, Priority.ALWAYS);

        Button deleteBTN = new Button("Delete");

        correctAnswerCB.add(validAnswerCB);
        textFields.add(answerTF);

        deleteBTN.setOnAction(e -> {
            if (answersContainer.getChildren().size() > MIN_ANSWERS) {
                answersContainer.getChildren().remove(answerRow);
                correctAnswerCB.remove(validAnswerCB);
                textFields.remove(answerTF);
                setAddAnswerBTNVisibility();
            } else {
                showErrorPopup("You must have at least 2 answers.");
            }
        });
        answerRow.getChildren().addAll(validAnswerCB, answerTF, deleteBTN);
        return answerRow;
    }

    /**
     * Retourne le texte des réponses dont la {@link CheckBox} est cochée.
     *
     * @return the list of text for all answers marked as correct.
     */
    List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        for (int i = 0; i < correctAnswerCB.size(); i++) {
            if (correctAnswerCB.get(i).isSelected()) {
                correctAnswers.add(textFields.get(i).getText());
            }
        }
        return correctAnswers;
    }

    /**
     * Returns the text entered in the question title field.
     *
     * @return the question title; may be empty.
     */
    String getTitle() {
        return questionTitle.getText();
    }

    /**
     * Displays a modal error popup with the provided message.
     * The application is paused until the popup is closed.
     *
     * @param message the error message to display to the user.
     */
    private void showErrorPopup(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Input error");
        alert.setHeaderText("Unable to save the question");
        alert.setContentText(message);
        alert.showAndWait(); // Pauses the application until the user clicks OK
    }

    @Override
    protected String getFXMLPath() {
        return "editor/McqEditor.fxml";
    }

    public interface IMcqEditorListener extends IListener {
        void createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
                throws InvalidQuestionException;
    }
}
