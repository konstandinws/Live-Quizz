package be.helha.clapetteChaussette.controller.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
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
        implements IQuizzListener, IOpenEditorListener, IMcqEditorListener {
    EditorService editorService;
    ValidateQuestionEditorService validateQuestionEditorService;
    NavigationService navigationService;
    Quizz quizz;
    TeacherDTO teacher;

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

    // IQuizzListener methods

    @Override
    public void onAddMcqRequested() {
        navigateToView(new McqEditorFX(), "Éditeur de Question QCM");
    }

    @Override
    public void onAddPollRequested() {
        // TODO
    }

    @Override
    public void onAddQuestionFreeRequested() {
        navigateToView(new OpenEditorFX(), "Éditeur de Question Libre");
    }

    @Override
    public void onSaveRequested() {
        this.saveQuizz();
    }

    // IOpenEditorListener methods

    @Override
    public void createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateOpenQuestion(title, answerTexts);
        quizz.addQuestion(editorService.createOpenQuestion(title, answerTexts, hasPoints));
    }

    // IMcqEditorListener methods

    @Override
    public void createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateMcqQuestion(title, answers);
        quizz.addQuestion(editorService.createMcqQuestion(title, answers, correctAnswers));
    }

    // some shit to move

    private void saveQuizz() { // a faire dans un service
        DbService dbService = new DbService();
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacher.uuid());
        dbService.saveQuizz(quizzDTO);
        dbService.saveQuestions(createQuestionDTOs(quizz.getQuestions()));
    }

    private List<QuestionDTO> createQuestionDTOs(List<Question> questions) { // a faire dans un service
        UUID quizzUuid = quizz.getUuid();
        List<QuestionDTO> questionsDTO = new ArrayList<QuestionDTO>();
        for (Question question : quizz.getQuestions()) {
            QuestionDTO questionDTO = new QuestionDTO(
                    UUID.randomUUID(),
                    quizzUuid,
                    question.getQuestion(),
                    question.getAnswers(),
                    question.getType());
            questionsDTO.add(questionDTO);
        }
        return questionsDTO;
    }
}
