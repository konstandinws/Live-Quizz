package be.helha.clapetteChaussette.controller.editor;

import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.PollEditorFX;
import be.helha.clapetteChaussette.view.PollEditorFX.IPollEditorListener;
import be.helha.clapetteChaussette.view.editor.McqEditorFX;
import be.helha.clapetteChaussette.view.editor.OpenEditorFX;
import be.helha.clapetteChaussette.view.editor.McqEditorFX.IMcqEditorListener;
import be.helha.clapetteChaussette.view.editor.OpenEditorFX.IOpenEditorListener;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX.IQuizzListener;

/**
 * Main controller for the editor features.
 * <p>
 * This controller manages the navigation between different question editors
 * (QCM, Open Question, etc.)
 * and handles the creation and saving of questions to the quizz being edited.
 * It interacts with the {@link EditorService} for business logic and the
 * {@link ValidateQuestionEditorService} for validating question data before
 * saving.
 * The controller also manages a reference to the current quizz and the teacher
 * creating it, allowing for proper association when saving to the database.
 * <p>
 */
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
            EditorService editorService,
            NavigationService navigationService,
            ValidateQuestionEditorService validateQuestionEditorService) {

        this.quizz = quizz;
        this.editorService = editorService;
        this.navigationService = navigationService;
        this.validateQuestionEditorService = validateQuestionEditorService;

        teacher = new TeacherDTO(
                UUID.randomUUID(),
                "test_prof_1",
                "mot_de_passe_haché");
        TeacherRepository teacherRepository = new TeacherRepository(); // i know this is bad but this gonna move when we
                                                                       // implement the login system
        teacherRepository.save(teacher);
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
    public void createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints)
            throws InvalidQuestionException {
        quizz.addQuestion(editorService.createOpenQuestion(title, answerTexts, hasPoints));
    }

    // endregion

    // #region IMcqEditorListener methods

    @Override
    public void createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        quizz.addQuestion(editorService.createMcqQuestion(title, answers, correctAnswers));
    }

    // endregion

    // #region IPollEditorListener methods

    @Override
    public void createPoll(String title, List<String> answers) throws InvalidQuestionException {
        quizz.addQuestion(editorService.createPollQuestion(title, answers));
    }

    // //#endregion
}
