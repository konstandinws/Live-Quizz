// be.helha.clapetteChaussette.AppFX
package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.client.controller.HomeController;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import be.helha.clapetteChaussette.server.repository.ITeacherRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuestionRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcQuizzRepository;
import be.helha.clapetteChaussette.server.repository.db.JdbcTeacherRepository;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.HomeViewFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    @Override
    public void start(Stage mainstage) throws Exception {
        NavigationService navigationService = new NavigationService(mainstage);
        ValidateQuestionEditorService validateService = new ValidateQuestionEditorService();

        IQuizzRepository quizzRepo = new JdbcQuizzRepository();
        IQuestionRepository questionRepo = new JdbcQuestionRepository();
        ITeacherRepository teacherRepo = new JdbcTeacherRepository();

        EditorService editorService = new EditorService(quizzRepo, questionRepo, validateService);

        HomeController homeController = new HomeController(navigationService, teacherRepo, editorService, validateService);
        homeController.navigateToView(new HomeViewFX(), "Clapette Chaussette", homeController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
