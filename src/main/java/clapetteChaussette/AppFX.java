package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.view.MainViewFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFX extends Application {

    private Stage primaryStage;
    private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/clapetteChaussette.fxml"));
        Parent root = loader.load();
        MainViewFX viewController = loader.getController();
        viewController.setController(mainController);
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
