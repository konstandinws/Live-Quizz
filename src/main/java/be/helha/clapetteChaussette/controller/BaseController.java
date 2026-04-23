package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.ViewFX;

public abstract class BaseController {
    /*
     * this is a base controller, it is not meant to be used directly, but to be
     * extended by other controllers, it contains common functionalities for all
     * controllers, such as navigation
     */
    protected NavigationService navigationService;

    /**
     * Navigates to the specified JavaFX view and assigns its dedicated controller
     * instance.
     * <p>
     * This method delegates the navigation action to the {@code navigationService}
     * using the
     * provided view and window title, then binds the given controller to that view
     * via
     * .
     *
     */

    public BaseController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public <C extends BaseController> void navigateToView(ViewFX<C> view, String title, C controller) {
        navigationService.navigateTo(view, title);
        view.setOwnController(controller);
    }
}
