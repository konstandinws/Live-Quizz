package be.helha.clapetteChaussette.client.view;

import javafx.scene.Scene;

public interface IDisplayable {
    /**
     * This interface must be implemented by views so that the navigation service
     * can display them.
     * 
     * Each IDisplayable must have an Initialize method.
     */

    Scene initializeView() throws Exception;
}
