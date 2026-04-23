package be.helha.clapetteChaussette.service;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.view.IDisplayable;
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

    public void navigateTo(IDisplayable diplayable, String title) {
        // save the state of the current scene before navigating to the new one
        Scene oldScene = this.stage.getScene();
        if (oldScene != null) {
            this.History.add(oldScene);
        }
        this.currentSceneIndex++;
        stage.setTitle(title);
        try {
            Scene scene = diplayable.initializeView();
            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
