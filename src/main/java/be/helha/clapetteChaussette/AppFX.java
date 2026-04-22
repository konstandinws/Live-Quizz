package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.repository.*;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private EditorController editorController;

    @Override
    public void start(Stage mainstage) throws Exception {
        NavigationService navigationService = new NavigationService(mainstage);
        ValidateQuestionEditorService validateQuestionEditorService = new ValidateQuestionEditorService();
        ITeacherRepository ITeacherRepository = new JdbcITeacherRepository();
        IQuestionRepository IQuestionRepository = new JdbcIQuestionRepository();
        IQuizzRepository IQuizzRepository = new JdbcIQuizzRepository();
        DbService dbService = new DbService(ITeacherRepository, IQuizzRepository, IQuestionRepository);
        EditorService editorService = new EditorService(dbService, validateQuestionEditorService);
        Quizz quizz = new Quizz("placeholder");
        editorController = new EditorController(quizz, editorService, navigationService, validateQuestionEditorService);
        editorController.navigateToView(new QuizzEditorFX(), "Éditeur de quizz", editorController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
