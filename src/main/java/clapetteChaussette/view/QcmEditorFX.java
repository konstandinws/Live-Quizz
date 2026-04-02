package clapetteChaussette.view;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QcmEditorFX {

    @FXML
    private Button addQuesionBTN;

    @FXML
    private TextField answ1Label;

    @FXML
    private TextField answ2Label;

    @FXML
    private TextField answ3Label;

    @FXML
    private TextField answ4Label;

    @FXML
    private CheckBox correctAnsw1;

    @FXML
    private CheckBox correctAnsw2;

    @FXML
    private CheckBox correctAnsw3;

    @FXML
    private CheckBox correctAnsw4;

    @FXML
    private Button deleteAnsw1;

    @FXML
    private Button deleteAnsw2;

    @FXML
    private Button deleteAnsw3;

    @FXML
    private Button deleteAnsw4;

    @FXML
    private TextField qestionTitle;

    @FXML
    private Button saveQuestionBTN;

    List<CheckBox> correctAnswerCB = Arrays.asList(correctAnsw1, correctAnsw2, correctAnsw3, correctAnsw4);
    List<TextField> textFields = Arrays.asList(answ1Label, answ2Label, answ3Label, answ4Label);

    @FXML
    QcmQuestion createQuestion(ActionEvent event) {
        String question = qestionTitle.getText();
        List<String> answers = getAnswers();
        String correctAnswer = getCorrectAnswer();

        return new QcmQuestion(question, answers, correctAnswer);
    }

    @FXML
    void addNewAnswer(ActionEvent event) {

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
