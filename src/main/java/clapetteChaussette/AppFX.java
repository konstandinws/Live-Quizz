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

public class AppFX extends Application  {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/questionfree.fxml"));
        Parent root = loader.load();
        MainViewFX controller = loader.getController();

        primaryStage.setTitle("Clapette Chaussette");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    

    public void afficherQuestion(Question question) throws Exception {
        // creer la question avec le service normalement
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/qcm.fxml")
        );
        Parent root = loader.load();
        QcmViewFX controller = loader.getController();

        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question QCM");
        nouvelleFenetre.setScene(new Scene(root));
        nouvelleFenetre.show();
    }
}
