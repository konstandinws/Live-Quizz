package be.helha.clapetteChaussette.controller.student;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.student.StudentWaitingViewFX.IStudentWaitingListener;

public class StudentWaitingController extends BaseController implements IStudentWaitingListener {

    public StudentWaitingController(NavigationService nav) {
        super(nav);
    }

    @Override
    public void onParticipantCountUpdated(int count) {
        // TODO: mettre à jour l'affichage via le réseau
    }
}
