package be.helha.clapetteChaussette.client.view;

import be.helha.clapetteChaussette.client.service.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public abstract class ViewFX<C extends IListener> implements IDisplayable {

    protected C listener;

    @FXML
    protected Button returnBTN;

    protected abstract String getFXMLPath();

    public Scene initializeView() throws Exception {
        String fxmlPath = "/clapetteChaussette/fxml/" + this.getFXMLPath();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(param -> this);
        Parent root = loader.load();
        return new Scene(root);
    }

    public void setOwnController(C controller) {
        this.listener = controller;
    }

    public void setNavigationService(NavigationService nav) {
        if (returnBTN != null) {
            returnBTN.setOnAction(e -> nav.goBack());
        }
    }

}
