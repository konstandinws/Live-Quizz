// be.helha.clapetteChaussette.AppFX
package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.controller.LoginController;
import be.helha.clapetteChaussette.repository.JdbcIQuestionRepository;
import be.helha.clapetteChaussette.repository.JdbcIQuizzRepository;
import be.helha.clapetteChaussette.repository.JdbcITeacherRepository;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.LoginViewFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    @Override
    public void start(Stage mainstage) throws Exception {
        NavigationService navigationService = new NavigationService(mainstage);
        ValidateQuestionEditorService validateService = new ValidateQuestionEditorService();

        JdbcITeacherRepository teacherRepository = new JdbcITeacherRepository();
        JdbcIQuestionRepository questionRepository = new JdbcIQuestionRepository();
        JdbcIQuizzRepository quizzRepository = new JdbcIQuizzRepository();

        DbService dbService = new DbService(teacherRepository, quizzRepository, questionRepository);
        EditorService editorService = new EditorService(dbService, validateService);

        // On lance le LoginController au démarrage
        LoginController loginController = new LoginController(navigationService, dbService, editorService,
                validateService);
        loginController.navigateToView(new LoginViewFX(), "Clapette Chaussette - Connexion", loginController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
