package clapetteChaussette.service;

import java.util.ArrayList;
import java.util.List;

import clapetteChaussette.controller.IController;
import clapetteChaussette.view.IDisplayable;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationService {

    Stage stage;
    List<Scene> History;
    int currentSceneIndex;

    public NavigationService(Stage primaryStage) {
        this.stage = primaryStage;
        this.History = new ArrayList<Scene>();
        this.currentSceneIndex = -1;
    }

    public void navigateTo(IDisplayable diplayable, IController controller, String title) {
        // save the state of the current scene before navigating to the new one
        Scene oldScene = this.stage.getScene();
        if (oldScene != null) {
            this.History.add(oldScene);
        }
        this.currentSceneIndex++;
        stage.setTitle(title);
        try {
            Scene scene = diplayable.InitializeView(controller);
            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
