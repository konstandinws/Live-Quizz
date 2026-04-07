package clapetteChaussette;

import clapetteChaussette.controller.QuestionnaireController;
import clapetteChaussette.service.NavigationService;
import clapetteChaussette.view.QuestionnaireFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private Stage mainStage;
    private QuestionnaireController questionnaireController;

    @Override
    public void start(Stage mainstage) throws Exception {
        this.mainStage = mainstage;
        NavigationService navigationService = new NavigationService(mainstage);
        this.questionnaireController = new QuestionnaireController(navigationService);
        QuestionnaireFX questionnaireView = new QuestionnaireFX();
        navigationService.navigateTo(questionnaireView, "Questionnaire");
        questionnaireView.setController(questionnaireController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
