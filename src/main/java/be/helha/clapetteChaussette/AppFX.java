// be.helha.clapetteChaussette.AppFX
package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.client.controller.HomeController;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.HomeViewFX;
import be.helha.clapetteChaussette.common.config.AppConfig;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppFX extends Application {

    @Override
    public void start(Stage mainstage) throws Exception {
        AppConfig config = AppConfig.getInstance();
        NavigationService navigationService = new NavigationService(mainstage);
        ValidateQuestionEditorService validateService = new ValidateQuestionEditorService();

        // Network connection shared across the whole application (login + save).
        NetworkController networkController = new NetworkController(config.getServerHost(), config.getServerPort());

        // Closing the window closes the socket, so the server detects the disconnect
        // (and ends/leaves the session) and the listener thread stops.
        mainstage.setOnCloseRequest(event -> {
            try {
                networkController.disconnect();
            } catch (IOException e) {
                System.err.println("Erreur à la fermeture de la connexion : " + e.getMessage());
            }
        });

        EditorService editorService = new EditorService(networkController, validateService);

        HomeController homeController = new HomeController(navigationService, editorService,
                validateService, networkController);
        homeController.navigateToView(new HomeViewFX(), "Clapette Chaussette", homeController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
