package be.helha.clapetteChaussette.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class ViewFX<C extends IListener> implements IDisplayable {

    protected C listener;

    protected abstract String getFXMLPath();

    public Scene InitializeView() throws Exception {
        String fxmlPath = "/clapetteChaussette/fxml/" + this.getFXMLPath();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }

    public void setOwnController(C controller) {
        this.listener = controller;
    }

}
