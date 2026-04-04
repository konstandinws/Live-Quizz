package clapetteChaussette.view;

import clapetteChaussette.controller.IController;
import javafx.scene.Scene;

public interface IDisplayable {
    /**
     * Cette interface est à implémenter par les vues pour que le service de
     * navigation puisse les afficher
     * 
     * Chaque IDisplayable doit avoir une méthode Initialize
     */

    Scene InitializeView(IController controller) throws Exception;
}
