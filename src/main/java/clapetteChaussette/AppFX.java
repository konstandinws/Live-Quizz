package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.view.MainViewFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFX extends Application implements MainViewFX.Listener {
    @Override
    public void start(Stage primaryStage) throws Exception {
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

    }
}
