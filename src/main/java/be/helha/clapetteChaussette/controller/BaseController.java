package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.ViewFX;

public abstract class BaseController {
    /**
     * this is a base controller, it is not meant to be used directly, but to be
     * extended by other controllers, it contains common functionalities for all
     * controllers, such as navigation
     */
    protected NavigationService navigationService;

    public void navigateToView(ViewFX view, String title) {
        view.setOwnController(this);
        navigationService.navigateTo(view, title);
    }
}
