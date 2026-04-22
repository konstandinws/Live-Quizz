package be.helha.clapetteChaussette.view.editor;

// VÉRIFIE BIEN CET IMPORT
import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.util.List;

public class OpenEditorFX extends ViewFX<EditorController> {

    @FXML
    private TextField questionTitle;

    @FXML
    private CheckBox pointsCheckBox;

    @FXML
    private List<TextField> answerFields;

    @FXML
    private void saveQuestion() {
        try {
            String title = questionTitle.getText();
            boolean hasPoints = pointsCheckBox.isSelected();

            // On transforme la liste de TextField en liste de String
            List<String> answerText = answerFields.stream()
                    .map(TextField::getText)
                    .toList();

            // L'appel fonctionnera si la méthode existe dans EditorController
            listener.createOpenQuestion(title, answerText, hasPoints);

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    protected String getFXMLPath() {
        return "OpenEditor.fxml";
    }
}