package be.helha.clapetteChaussette.client.view.ControlerComponent;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Minimal helper to display a connection/error popup to the user.
 * <p>
 * Safe to call from any thread: the dialog is always shown on the JavaFX
 * Application Thread via {@link Platform#runLater}.
 * </p>
 */
public final class ErrorPopup {

    private ErrorPopup() {
    }

    /**
     * Displays a non-blocking error dialog with the given message.
     *
     * @param message the message to show to the user.
     */
    public static void show(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de connexion");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
