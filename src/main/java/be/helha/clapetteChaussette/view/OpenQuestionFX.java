package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.question.OpenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

// 1. On précise le contrôleur associé (OpenController)
public class OpenQuestionFX extends ViewFX<OpenController> {
    @FXML
    private Button envoyerBtn;

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private void initialize() {
        // Initialisation vide, le texte sera mis à jour via setOpenQuestion
    }

    @FXML
    private void handleSendAction() {
        if (listener != null && textArea != null) {
            // 2. On n'envoie que le texte, conformément à l'interface IQuestionListener
            listener.validate(textArea.getText());
        }
    }

    @Override
    protected String getFXMLPath() {
        return "open.fxml";
    }

    // 3. Plus besoin de setOwnViewController, ViewFX possède déjà setOwnController
}
