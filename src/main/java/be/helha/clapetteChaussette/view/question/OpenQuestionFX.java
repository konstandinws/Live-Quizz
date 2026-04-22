package be.helha.clapetteChaussette.view.question;

import be.helha.clapetteChaussette.controller.question.OpenController;
import be.helha.clapetteChaussette.view.IQuestionListener;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenQuestionFX extends ViewFX<OpenController> {
    @FXML
    private Button envoyerBtn;

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSendAction() {
        if (textArea != null) {
            listener.validate(textArea.getText());
        }
    }

    @Override
    protected String getFXMLPath() {
        return "open.fxml";
    }

    public interface IOpenListener extends IQuestionListener<String> {
        public void validate(String aws);
    }
}
