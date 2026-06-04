// be.helha.clapetteChaussette.AppFX
package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.client.controller.HomeController;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.HomeViewFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3001;

    @Override
    public void start(Stage mainstage) throws Exception {
        NavigationService navigationService = new NavigationService(mainstage);
        ValidateQuestionEditorService validateService = new ValidateQuestionEditorService();

        // Network connection shared across the whole application (login + save).
        NetworkController networkController = new NetworkController(SERVER_HOST, SERVER_PORT);

        EditorService editorService = new EditorService(networkController, validateService);

        HomeController homeController = new HomeController(navigationService, editorService,
                validateService, networkController);
        homeController.navigateToView(new HomeViewFX(), "Clapette Chaussette", homeController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
