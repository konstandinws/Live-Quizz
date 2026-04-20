package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.controller.IViewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenQuestionFX extends ViewFX {
    @FXML
    private Button envoyerBtn;

    @FXML
    private Label questionFree;

    @FXML
    private TextArea textArea;

    private IQuestionListener<String> listener;

    @FXML
    private void initialize() {
    }

    public void setController(IQuestionListener<String> listener) {
        this.listener = listener;
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

    @Override
    public void setOwnViewController(IViewController controller) {
        if (controller instanceof IQuestionListener) {
            this.listener = (IQuestionListener<String>) controller;
        } else {
            throw new IllegalArgumentException("Controller must implement IQuestionListener<String>");
        }
    }

    public interface IOpenListener extends IQuestionListener<String> {
        public void validate(String aws);
    }
}
