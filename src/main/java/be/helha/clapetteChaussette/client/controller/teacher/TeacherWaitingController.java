package be.helha.clapetteChaussette.client.controller.teacher;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;

public class TeacherWaitingController extends BaseController
        implements ITeacherWaitingListener {
    private final QuizzDTO quizz;

    public TeacherWaitingController(NavigationService nav, QuizzDTO quizz) {
        super(nav);
        this.quizz = quizz;
    }

    @Override
    public void onStartQuizzRequested() {
        // TODO: lancer le quiz via le serveur omg je peu le faire c'est trop ezzzz

    }
}
