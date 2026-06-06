package be.helha.clapetteChaussette.client.controller.editor;

import java.io.IOException;
import java.util.List;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.common.models.Quizz;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import javafx.application.Platform;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.editor.McqEditorFX;
import be.helha.clapetteChaussette.client.view.editor.OpenEditorFX;
import be.helha.clapetteChaussette.client.view.editor.PollEditorFX;
import be.helha.clapetteChaussette.client.view.editor.QuizzEditorFX;
import be.helha.clapetteChaussette.client.view.editor.McqEditorFX.IMcqEditorListener;
import be.helha.clapetteChaussette.client.view.editor.OpenEditorFX.IOpenEditorListener;
import be.helha.clapetteChaussette.client.view.editor.PollEditorFX.IPollEditorListener;
import be.helha.clapetteChaussette.client.view.editor.QuizzEditorFX.IQuizzListener;

public class EditorController
        extends BaseController
        implements IQuizzListener, IOpenEditorListener, IMcqEditorListener, IPollEditorListener,
        ClientThreadHandler.Listener {

    // #region Fields
    EditorService editorService;
    ValidateQuestionEditorService validateQuestionEditorService;
    TeacherQuizzChoicesController parentController;
    NetworkController networkController;
    Quizz quizz;
    TeacherDTO teacher;

    // endregion

    // #region Constructor

    /**
     * Constructor for the EditorController.
     * 
     * @param quizz                         The quizz being edited.
     * @param editorService                 Service for handling editor-related
     *                                      business logic.
     * @param navigationService             Service for handling navigation between
     *                                      views.
     * @param validateService Service for validating question data
     *                                      before saving.
     */
    public EditorController(Quizz quizz,
            TeacherDTO teacher,
            EditorService editorService,
            NavigationService navigationService,
            ValidateQuestionEditorService validateService, TeacherQuizzChoicesController parentController,
            NetworkController networkController) {
        super(navigationService);
        this.quizz = quizz;
        this.teacher = teacher;
        this.editorService = editorService;
        this.validateQuestionEditorService = validateService;
        this.parentController = parentController;
        this.networkController = networkController;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            ErrorPopup.show("Impossible de joindre le serveur.");
        }
    }

    // #endregion

    // #region IQuizzListener methods

    @Override
    public void onAddMcqRequested(String title) {
        quizz.setName(title);
        navigateToView(new McqEditorFX(), "Éditeur de Question QCM", this);
    }

    @Override
    public void onAddPollRequested(String title) {
        quizz.setName(title);
        navigateToView(new PollEditorFX(), "Éditeur de Sondage", this);
    }

    @Override
    public void onAddQuestionFreeRequested(String title) {
        quizz.setName(title);
        navigateToView(new OpenEditorFX(), "Éditeur de Question Libre", this);
    }

    @Override
    public void onSaveRequested(String title) {
        if (title == null || title.isBlank()) {
            ErrorPopup.show("Le titre du questionnaire est obligatoire.");
            return;
        }
        if (quizz.getQuestions().isEmpty()) {
            ErrorPopup.show("Le questionnaire doit contenir au moins une question.");
            return;
        }
        quizz.setName(title);

        // Navigation back to the list happens when the server response arrives.
        try {
            editorService.saveQuizz(quizz, teacher.uuid());
        } catch (IOException e) {
            ErrorPopup.show("Impossible de joindre le serveur.");
        }
    }

    @Override
    public void onSaveQuizzResponse(SaveQuizzResponse saveQuizzResponse) {
        Platform.runLater(() -> {
            if (saveQuizzResponse.isSuccess()) {
                // Once saved, this editor is done: stop listening so it doesn't react
                // to future saves.
                networkController.removeListener(this);
                // Deterministically show the questionnaires list (no reliance on the
                // navigation history, which breaks if several editors were opened).
                this.parentController.showQuizzes(saveQuizzResponse.getQuizzes());
            } else {
                ErrorPopup.show(saveQuizzResponse.getMessage());
            }
        });
    }
    // endregion

    // #region IOpenEditorListener methods

    @Override
    public void createOpenQuestion(String title, List<String> answerTexts, List<Boolean> correctAnswers,
            boolean hasPoints)
            throws InvalidQuestionException {
        quizz.addQuestion(editorService.createOpenQuestion(title, answerTexts, correctAnswers, hasPoints));
        showQuizzEditor();
    }

    // endregion

    // #region IMcqEditorListener methods

    @Override
    public void createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        quizz.addQuestion(editorService.createMcqQuestion(title, answers, correctAnswers));
        showQuizzEditor();
    }

    // endregion

    // #region IPollEditorListener methods

    @Override
    public void createPoll(String title, List<String> answers) throws InvalidQuestionException {
        quizz.addQuestion(editorService.createPollQuestion(title, answers));
        showQuizzEditor();
    }

    // #endregion

    // #region Private Methods

    /**
     * Réaffiche l'éditeur de quiz en listant les questions déjà ajoutées.
     */
    private void showQuizzEditor() {
        QuizzEditorFX editorView = new QuizzEditorFX();
        replaceView(editorView, "Éditeur de Quiz", this);
        editorView.setTitle(quizz.getName());
        editorView.displayQuestions(quizz.getQuestions());
    }

    // #endregion
}
