// be.helha.clapetteChaussette.view.LoginViewFX
package be.helha.clapetteChaussette.client.view;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.LoginController;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewFX extends ViewFX<LoginController> {

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        try {
            listener.onLoginRequested(nameField.getText(), passwordField.getText());
        } catch (IOException e) {
            ErrorPopup.show("Erreur lors de la connexion au serveur: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegister() {
        try {
            listener.onRegisterRequested(nameField.getText(), passwordField.getText());
        } catch (IOException e) {
            ErrorPopup.show("Erreur lors de l'inscription au serveur: " + e.getMessage());
        }
    }

    @Override
    protected String getFXMLPath() {
        return "login.fxml";
    }

    @FXML
    private void initialize() {
    }

    public interface ILoginListener extends IListener {
        void onLoginRequested(String name, String password) throws IOException;

        void onRegisterRequested(String name, String password) throws IOException;
    }
}
