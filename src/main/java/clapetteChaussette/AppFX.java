package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.MainViewFX;
import clapetteChaussette.view.QcmViewFX;
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
}
