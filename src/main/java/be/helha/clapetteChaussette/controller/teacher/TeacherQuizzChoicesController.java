package be.helha.clapetteChaussette.controller.teacher;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.editor.EditorService;
import be.helha.clapetteChaussette.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX;
import be.helha.clapetteChaussette.view.teacher.TeacherQuizzChoicesViewFX.ITeacherQuizzChoicesListener;

import java.util.List;

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
        List<QuizzDTO> quizzes = editorService.getQuizzesForTeacher(teacher.uuid());
        editorView.displayQuizzes(quizzes);
    }
}
