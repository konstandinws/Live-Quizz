package be.helha.clapetteChaussette.view.question;

import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.IQuestionListener;
import be.helha.clapetteChaussette.view.ViewFX;
import be.helha.clapetteChaussette.view.question.OpenQuestionFX.IOpenListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenQuestionFX extends ViewFX<IOpenListener> {
    @FXML
    private Button submitButton;

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
        return "question/open.fxml";
    }

    public interface IOpenListener extends IListener, IQuestionListener<String> {
        public void validate(String aws);
    }
}
