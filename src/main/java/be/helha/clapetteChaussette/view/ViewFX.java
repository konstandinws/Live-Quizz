package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.IViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class ViewFX implements IDisplayable {

    protected IViewController controller;

    protected abstract String getFXMLPath();

    public Scene InitializeView() throws Exception {
        String fxmlPath = "/clapetteChaussette/fxml/" + this.getFXMLPath();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }

    public void setOwnViewController(IViewController controller) {
        this.controller = controller;
    }
}
