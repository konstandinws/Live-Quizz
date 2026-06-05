package be.helha.clapetteChaussette.client.controller.teacher;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;
import be.helha.clapetteChaussette.common.models.Quizz;

public class TeacherWaitingController extends BaseController
        implements ITeacherWaitingListener {
    private final Quizz quizz;

    public TeacherWaitingController(NavigationService nav, Quizz quizz) {
        super(nav);
        this.quizz = quizz;
    }

    @Override
    public void onStartQuizzRequested() {
        // TODO: lancer le quiz via le serveur

    }
}
