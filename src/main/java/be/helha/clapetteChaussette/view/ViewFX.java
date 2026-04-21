package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class ViewFX implements IDisplayable {

    protected BaseController controller;

    protected abstract String getFXMLPath();

    public Scene InitializeView() throws Exception {
        String fxmlPath = "/clapetteChaussette/fxml/" + this.getFXMLPath();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }

    public void setOwnController(BaseController controller) {
        this.controller = controller;
    }
}
