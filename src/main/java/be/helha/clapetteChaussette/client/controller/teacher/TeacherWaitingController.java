package be.helha.clapetteChaussette.client.controller.teacher;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;

public class TeacherWaitingController extends BaseController implements ITeacherWaitingListener {

    public TeacherWaitingController(NavigationService nav) {
        super(nav);
    }

    @Override
    public void onStartQuizzRequested() {
        // TODO: lancer le quiz via le serveur
    }
}
