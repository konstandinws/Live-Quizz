package be.helha.clapetteChaussette.client.controller.teacher;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;

public class TeacherLiveController extends BaseController implements ClientThreadHandler.Listener {

    private final NetworkController networkController;

    public TeacherLiveController(NavigationService nav, NetworkController networkController) {
        super(nav);
        this.networkController = networkController;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            ErrorPopup.show("Erreur de connexion au serveur : " + e.getMessage());
        }

    }

}
