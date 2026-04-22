// be.helha.clapetteChaussette.view.LoginViewFX
package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.LoginController;
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
        listener.onLoginRequested(nameField.getText(), passwordField.getText());
    }

    @FXML
    private void handleRegister() {
        listener.onRegisterRequested(nameField.getText(), passwordField.getText());
    }

    @Override
    protected String getFXMLPath() {
        return "login.fxml";
    }

    @FXML
    private void initialize() {
    }

    public interface ILoginListener {
        void onLoginRequested(String name, String password);

        void onRegisterRequested(String name, String password);
    }
}
