package clapetteChaussette;

import clapetteChaussette.controller.MainController;
import clapetteChaussette.service.NavigationService;
import clapetteChaussette.view.MainViewFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private Stage mainStage;
    private MainController mainController;

    @Override
    public void start(Stage mainstage) throws Exception {
        this.mainStage = mainstage;
        NavigationService navigationService = new NavigationService(mainstage);
        this.mainController = new MainController(navigationService);
        navigationService.navigateTo(new MainViewFX(), mainController, "Main View");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
