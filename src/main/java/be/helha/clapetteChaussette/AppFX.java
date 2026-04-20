package be.helha.clapetteChaussette;

import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppFX extends Application {

    private EditorController editorController;

    @Override
    public void start(Stage mainstage) throws Exception {
        NavigationService navigationService = new NavigationService(mainstage);
        EditorService editorService = new EditorService();
        Quizz quizz = new Quizz("placeholder");
        editorController = new EditorController(quizz, editorService, navigationService);
        editorController.navigateToQuizzEditor();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
