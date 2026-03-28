package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.MainViewFX;
import clapetteChaussette.view.QcmViewFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppFX extends Application implements MainViewFX.Listener {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/clapetteChaussette.fxml"));
        Parent root = loader.load();
        MainViewFX controller = loader.getController();
        controller.setListener(this);
        primaryStage.setTitle("Clapette Chaussette");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onQcmLaunch() {
        String question = "que vaut 12 + 5 ?";
        ArrayList<String> answers = new ArrayList<>();
        answers.add("4");
        answers.add("12");
        answers.add("15");
        answers.add("17");
        String correctAnswer = "17";
        QcmQuestion qcm = new QcmQuestion(question, answers, correctAnswer);
        try {
            afficherQuestion(qcm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void afficherQuestion(Question question) throws Exception {
        // creer la question avec le service normalement
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/qcm.fxml")
        );
        Parent root = loader.load();
        QcmViewFX controller = loader.getController();
        controller.initQcm((QcmQuestion) question);
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question QCM");
        nouvelleFenetre.setScene(new Scene(root));
        nouvelleFenetre.show();
    }
}
