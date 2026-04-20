package be.helha.clapetteChaussette.view;

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

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.controller.editor.OpenEditorController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;

public class OpenEditorFX extends ViewFX {

    @FXML
    private TextField questionTitle; // Field where the teacher will enter the question
    @FXML
    private VBox answersContainer; // Container where the created answers are stacked
    @FXML
    private CheckBox pointsCheckBox; // Checkbox to enable or disable points
    @FXML
    private Button addAnswerBTN; // Button to add a new answer variant

    // Link to the controller
    private OpenEditorController controller;
    private List<TextField> answerFields = new ArrayList<>();

    // Called when the page initializes to add a first answer field
    @FXML
    public void initialize() {
        addNewAnswer();
    }

    @FXML
    private void addNewAnswer() {
        HBox row = createAnswerRow();
        answersContainer.getChildren().add(row);
    }

    private HBox createAnswerRow() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5));

        TextField tf = new TextField();
        tf.setPromptText("Accepted answer variant...");
        HBox.setHgrow(tf, Priority.ALWAYS);
        answerFields.add(tf);

        Button deleteBtn = new Button("X");
        deleteBtn.setOnAction(e -> {
            if (answerFields.size() > 1) {
                answersContainer.getChildren().remove(row);
                answerFields.remove(tf);
            }
        });

        row.getChildren().addAll(tf, deleteBtn);
        return row;
    }

    @FXML
    private void saveQuestion() {
        try {
            String title = questionTitle.getText();
            boolean hasPoints = pointsCheckBox.isSelected();
            List<String> answerText = answerFields.stream().map(TextField::getText).toList();

            controller.createOpenQuestion(title, answerText, hasPoints);

        } catch (InvalidQuestionException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    @Override
    protected String getFXMLPath() {
        return "OpenEditor.fxml";
    }

    @Override
    public void setOwnViewController(IViewController controller) {
        if (controller instanceof OpenEditorController) {
            this.controller = (OpenEditorController) controller;
        } else {
            throw new IllegalArgumentException("Controller must be an instance of OpenEditorController");
        }
    }
}
