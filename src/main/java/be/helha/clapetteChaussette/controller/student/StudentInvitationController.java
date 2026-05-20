package be.helha.clapetteChaussette.controller.student;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.student.StudentInvitationViewFX.IStudentInvitationListener;
import be.helha.clapetteChaussette.view.student.StudentWaitingViewFX;

public class StudentInvitationController extends BaseController implements IStudentInvitationListener {

    public StudentInvitationController(NavigationService nav) {
        super(nav);
    }

    @Override
    public void onJoinRequested(String code) {
        // TODO: valider le code avec le serveur
        StudentWaitingController waitingCtrl = new StudentWaitingController(navigationService);
        navigateToView(new StudentWaitingViewFX(), "En attente du lancement...", waitingCtrl);
    }
}
