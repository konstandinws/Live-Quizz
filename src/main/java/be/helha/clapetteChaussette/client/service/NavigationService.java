package be.helha.clapetteChaussette.client.service;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.client.view.IDisplayable;
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

    public void goBack() {
        if (!History.isEmpty()) {
            Scene previous = History.remove(History.size() - 1);
            currentSceneIndex--;
            stage.setScene(previous);
            stage.show();
        }
    }

    /**
     * Affiche {@code displayable} à la place de l'écran courant tout en supprimant
     * le dernier écran empilé dans l'historique.
     * <p>
     * Sert à revenir d'un sous-éditeur (ex. éditeur de QCM) vers l'éditeur de quiz :
     * on ne veut pas empiler le détour, sinon le bouton « Retour » repasserait par
     * l'éditeur de question au lieu de revenir à la liste des questionnaires.
     */
    public void returnAndReplace(IDisplayable displayable, String title) {
        if (!History.isEmpty()) {
            // enlève l'écran empilé lors de l'ouverture du sous-éditeur
            History.remove(History.size() - 1);
            currentSceneIndex--;
        }
        stage.setTitle(title);
        try {
            stage.setScene(displayable.initializeView());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
