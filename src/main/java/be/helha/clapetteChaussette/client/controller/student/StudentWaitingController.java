package be.helha.clapetteChaussette.client.controller.student;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.student.StudentWaitingViewFX.IStudentWaitingListener;

public class StudentWaitingController extends BaseController implements IStudentWaitingListener {

    public StudentWaitingController(NavigationService nav) {
        super(nav);
    }

    @Override
    public void onParticipantCountUpdated(int count) {
        // TODO: mettre à jour l'affichage via le réseau
    }
}
