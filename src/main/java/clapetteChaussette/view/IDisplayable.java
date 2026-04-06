package clapetteChaussette.view;

import javafx.scene.Scene;

public interface IDisplayable {
    /**
     * Cette interface est à implémenter par les vues pour que le service de
     * navigation puisse les afficher
     * 
     * Chaque IDisplayable doit avoir une méthode Initialize
     */

    Scene InitializeView() throws Exception;
}
