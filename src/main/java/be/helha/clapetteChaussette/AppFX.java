package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.controller.QuizzController;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.QuizzFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private Stage mainStage;
    private QuizzController questionnaireController;

    @Override
    public void start(Stage mainstage) throws Exception {
        this.mainStage = mainstage;
        NavigationService navigationService = new NavigationService(mainstage);
        this.questionnaireController = new QuizzController(navigationService);
        QuizzFX questionnaireView = new QuizzFX();
        navigationService.navigateTo(questionnaireView, "Editeur de questionnaire");
        questionnaireView.setOwnViewController(questionnaireController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
