package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.service.NavigationService;
import clapetteChaussette.view.MainViewFX;
import clapetteChaussette.view.QcmEditorFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFX extends Application {

    private Stage mainStage;
    private MainController mainController;

    @Override
    public void start(Stage mainstage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/QcmEditor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        mainstage.setScene(scene);
        mainstage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
