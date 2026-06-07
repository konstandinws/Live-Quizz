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

    // The view this controller currently drives. Used to surface errors on screen:
    // the controller says *what* went wrong, the view decides *how* to show it.
    private ViewFX<?> activeView;

    protected BaseController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    /**
     * Reports an error to the user. The actual presentation (a popup) is the view's
     * responsibility, so controllers never touch the popup themselves.
     */
    protected void showError(String message) {
        if (activeView != null) {
            activeView.showError(message);
        } else {
            // No screen bound yet (e.g. a connection error during construction).
            System.err.println("Erreur (aucune vue à l'écran) : " + message);
        }
    }

    /**
     * Navigates to the specified JavaFX view and assigns its dedicated controller
     * instance.
     */
    public <C extends IListener> void navigateToView(ViewFX<C> view, String title, C controller) {
        navigationService.navigateTo(view, title);
        view.setNavigationService(navigationService);
        view.setOwnController(controller);
        bindCurrentView(view, controller);
    }

    /**
     * Comme {@link #navigateToView} mais remplace l'écran courant sans empiler le
     * détour dans l'historique (retour d'un sous-éditeur vers l'éditeur de quiz).
     */
    public <C extends IListener> void replaceView(ViewFX<C> view, String title, C controller) {
        navigationService.returnAndReplace(view, title);
        view.setNavigationService(navigationService);
        view.setOwnController(controller);
        bindCurrentView(view, controller);
    }

    // Remembers the view on the controller that owns it (often a different controller
    // than the one navigating), so that controller can later show errors on it.
    private static void bindCurrentView(ViewFX<?> view, IListener controller) {
        if (controller instanceof BaseController) {
            ((BaseController) controller).activeView = view;
        }
    }
}
