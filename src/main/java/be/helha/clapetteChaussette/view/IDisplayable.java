package be.helha.clapetteChaussette.view;

import javafx.scene.Scene;

public interface IDisplayable {
    /**
     * This interface must be implemented by views so that the navigation service
     * can display them.
     * 
     * Each IDisplayable must have an Initialize method.
     */

    Scene InitializeView() throws Exception;
}
