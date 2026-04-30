package be.helha.clapetteChaussette.view.question;

import be.helha.clapetteChaussette.view.IListener;
import be.helha.clapetteChaussette.view.IQuestionView;
import be.helha.clapetteChaussette.view.ViewFX;
import be.helha.clapetteChaussette.view.question.OpenQuestionFX.IOpenListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class OpenQuestionFX extends ViewFX<IOpenListener> implements IQuestionView {
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
        listener.onOpenAnswerSelected(textArea.getText());
    }

    @Override
    protected String getFXMLPath() {
        return "question/open.fxml";
    }

    @Override
    public void showWaiting() {
        submitButton.setDisable(true);
        textArea.setDisable(true);
    }

    @Override
    public void showCorrect() {
        textArea.setBackground(Background.fill(Color.GREEN));
        questionFree.setText("Correct!");
    }

    @Override
    public void showIncorrect() {
        textArea.setBackground(Background.fill(Color.RED));
        questionFree.setText("Incorrect!");
    }
    public interface IOpenListener extends IListener {
        void onOpenAnswerSelected(String answer);
    }
}
