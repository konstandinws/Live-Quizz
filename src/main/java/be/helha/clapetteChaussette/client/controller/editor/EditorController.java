package be.helha.clapetteChaussette.client.controller.editor;

import java.util.List;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.common.models.Quizz;
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
        implements IQuizzListener, IOpenEditorListener, IMcqEditorListener, IPollEditorListener {

    // #region Fields

    EditorService editorService;
    ValidateQuestionEditorService validateQuestionEditorService;
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
     * @param validateQuestionEditorService Service for validating question data
     *                                      before saving.
     */
    public EditorController(Quizz quizz,
            TeacherDTO teacher,
            EditorService editorService,
            NavigationService navigationService,
            ValidateQuestionEditorService validateService) {
        super(navigationService);
        this.quizz = quizz;
        this.teacher = teacher;
        this.editorService = editorService;
        this.validateQuestionEditorService = validateService;
    }

    // #endregion

    // #region IQuizzListener methods

    @Override
    public void onAddMcqRequested() {
        navigateToView(new McqEditorFX(), "Éditeur de Question QCM", this);
    }

    @Override
    public void onAddPollRequested() {
        navigateToView(new PollEditorFX(), "Éditeur de Sondage", this);
    }

    @Override
    public void onAddQuestionFreeRequested() {
        navigateToView(new OpenEditorFX(), "Éditeur de Question Libre", this);
    }

    @Override
    public void onSaveRequested() {
        editorService.saveQuizz(quizz, teacher.uuid());
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
        navigateToView(editorView, "Éditeur de Quiz", this);
        editorView.displayQuestions(quizz.getQuestions());
    }

    // #endregion
}
