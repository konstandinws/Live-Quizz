package clapetteChaussette.controller;

import java.util.ArrayList;
import java.util.List;

import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.IMainListener;
import clapetteChaussette.view.IQcmView;
import clapetteChaussette.view.QcmViewFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements IMainListener {

    @Override
    public void onQcmLaunch() {
        String question = "que vaut 12 + 5 ?";
        List<String> answers = new ArrayList<>();
        answers.add("4");
        answers.add("12");
        answers.add("15");
        answers.add("17");
        String correctAnswer = "17";
        QcmQuestion qcm = new QcmQuestion(question, answers, correctAnswer);
        try {
            afficherQcm(qcm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPollLaunch() {
        System.out.println("Poll launched");
        // launch new view
    }

    @Override
    public void onFreeLaunch() {
        System.out.println("Free launched");
        // launch new view
    }

    public void afficherQcm(Question question) throws Exception {
        // creer la question avec le service normalement
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/qcm.fxml"));
        Parent root = loader.load();
        QcmViewFX controller = loader.getController();
        IQcmView viewController = new QcmController();
        controller.initialize(viewController, (QcmQuestion) question);
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question QCM");
        nouvelleFenetre.setScene(new Scene(root, 600, 400));
        nouvelleFenetre.show();
    }
}