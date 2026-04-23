// be.helha.clapetteChaussette.AppFX
package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.controller.LoginController;
import be.helha.clapetteChaussette.repository.IQuestionRepository;
import be.helha.clapetteChaussette.repository.IQuizzRepository;
import be.helha.clapetteChaussette.repository.ITeacherRepository;
import be.helha.clapetteChaussette.repository.JdbcQuestionRepository;
import be.helha.clapetteChaussette.repository.JdbcQuizzRepository;
import be.helha.clapetteChaussette.repository.JdbcTeacherRepository;
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

        IQuizzRepository QuizzRepo = new JdbcQuizzRepository();
        IQuestionRepository QuestionRepo = new JdbcQuestionRepository();
        ITeacherRepository TeacherRepo = new JdbcTeacherRepository();

        EditorService editorService = new EditorService(QuizzRepo, QuestionRepo, validateService);

        LoginController loginController = new LoginController(navigationService, TeacherRepo, editorService,
                validateService);
        loginController.navigateToView(new LoginViewFX(), "Clapette Chaussette - Connexion", loginController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
