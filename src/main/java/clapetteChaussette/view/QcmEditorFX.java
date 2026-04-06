package clapetteChaussette.view;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QcmEditorFX implements IDisplayable{

    @FXML
    private VBox answerContainer;
    private TextField questionTitle;

    List<CheckBox> correctAnswerCB;
    List<TextField> textFields;

    @FXML
    public void initialize() {
        for(int i= 0; i < 4; i++) {

        }
    }

    @FXML
    private void addNewAnswer() {
        createAnswerRow();
    }


    private HBox createAnswerRow() {
        HBox answerRow = new HBox();

        CheckBox validAnswerCB = new CheckBox();
        TextField answerTF = new TextField();
        Button deleteBTN = new Button();

        deleteBTN.setOnAction(e -> {
            if (answerContainer.getChildren().size() > 2){
                answerContainer.getChildren().remove(answerRow);
            }
            else {
                throw new RuntimeException("vous devez avoir au minimum 2 réponses");
            }
        });

        answerContainer.getChildren().addAll(validAnswerCB, answerTF, deleteBTN);
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

    @Override
    public Scene InitializeView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/QcmEditor.fxml"));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }
}
