package be.helha.clapetteChaussette.client.controller;

import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;

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

    protected BaseController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public <C extends IListener> void navigateToView(ViewFX<C> view, String title, C controller) {
        navigationService.navigateTo(view, title);
        view.setNavigationService(navigationService);
        view.setOwnController(controller);
    }

    /**
     * Comme {@link #navigateToView} mais remplace l'écran courant sans empiler le
     * détour dans l'historique (retour d'un sous-éditeur vers l'éditeur de quiz).
     */
    public <C extends IListener> void replaceView(ViewFX<C> view, String title, C controller) {
        navigationService.returnAndReplace(view, title);
        view.setNavigationService(navigationService);
        view.setOwnController(controller);
    }
}
