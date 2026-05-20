package be.helha.clapetteChaussette.controller.teacher;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;

public class TeacherWaitingController extends BaseController implements ITeacherWaitingListener {

    public TeacherWaitingController(NavigationService nav) {
        super(nav);
    }

    @Override
    public void onStartQuizzRequested() {
        // TODO: lancer le quiz via le serveur
    }
}
