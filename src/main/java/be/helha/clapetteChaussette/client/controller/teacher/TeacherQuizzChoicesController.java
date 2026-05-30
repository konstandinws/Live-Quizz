package be.helha.clapetteChaussette.client.controller.teacher;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.controller.editor.EditorController;
import be.helha.clapetteChaussette.common.models.Quizz;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.editor.QuizzEditorFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX.ITeacherQuizzChoicesListener;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;

public class TeacherQuizzChoicesController extends BaseController implements ITeacherQuizzChoicesListener {

    private final TeacherDTO teacher;
    private final EditorService editorService;
    private final ValidateQuestionEditorService validateService;

    public TeacherQuizzChoicesController(NavigationService nav, TeacherDTO teacher,
                                         EditorService editorService, ValidateQuestionEditorService validateService) {
        super(nav);
        this.teacher = teacher;
        this.editorService = editorService;
        this.validateService = validateService;
    }

    @Override
    public void onNewQuizzRequested() {
        Quizz quizz = new Quizz("Nouveau Quiz");
        EditorController editorCtrl = new EditorController(quizz, teacher, editorService, navigationService, validateService);
        QuizzEditorFX editorView = new QuizzEditorFX();
        navigateToView(editorView, "Éditeur de Quiz", editorCtrl);
        editorView.displayQuestions(quizz.getQuestions());
    }

    @Override
    public void onLaunchQuizzRequested(QuizzDTO quizz) {
        TeacherWaitingController waitingCtrl = new TeacherWaitingController(navigationService);
        TeacherWaitingViewFX waitingView = new TeacherWaitingViewFX();
        navigateToView(waitingView, "En attente de lancement", waitingCtrl);
    }
}
