package clapetteChaussette.view;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QcmEditorFX{

    @FXML
    private VBox answersContainer;
    public TextField questionTitle;

    private List<CheckBox> correctAnswerCB = new ArrayList<>();
    private List<TextField> textFields = new ArrayList<>();

    @FXML
    public void initialize() {
        for(int i= 0; i < 4; i++) {
            answersContainer.getChildren().add(createAnswerRow());
        }
    }

    @FXML
    private void addNewAnswer() {
        answersContainer.getChildren().add(createAnswerRow());
    }

    @FXML
    private void createQuestion() {}


    private HBox createAnswerRow() {
        HBox answerRow = new HBox();
        answerRow.setAlignment(Pos.CENTER_LEFT);
        answerRow.setPadding(new Insets(5, 10, 5, 10));

        CheckBox validAnswerCB = new CheckBox();

        TextField answerTF = new TextField();
        HBox.setHgrow(answerTF, Priority.ALWAYS);

        Button deleteBTN = new Button("Supprimer");

        correctAnswerCB.add(validAnswerCB);
        textFields.add(answerTF);

        deleteBTN.setOnAction(e -> {
            if (answersContainer.getChildren().size() > 2){
                answersContainer.getChildren().remove(answerRow);
                correctAnswerCB.remove(validAnswerCB);
                textFields.remove(answerTF);
            }
            else {
                throw new RuntimeException("vous devez avoir au minimum 2 réponses");
            }
        });

        answerRow.getChildren().addAll(validAnswerCB, answerTF, deleteBTN);
        return answerRow;
    }

    List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        for(TextField field : textFields) {
            answers.add(field.getText());
        }
        return answers;
    }

    String getCorrectAnswer() {
        // gérer le cas ou l'uitlisateur ne coche rien, surement renvoyer une erreur pour le controleur
        for(int i = 0; i < correctAnswerCB.size(); i++) {
            if(correctAnswerCB.get(i).isSelected()) {
                return textFields.get(i).getText();
            }
        }
        return "Pas de réponse";
    }
}
