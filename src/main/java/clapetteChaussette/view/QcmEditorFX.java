package clapetteChaussette.view;

import clapetteChaussette.controller.QcmEditorController;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class QcmEditorFX{

    @FXML
    private VBox answersContainer;
    @FXML
    private TextField questionTitle;
    @FXML
    private Button addAnswerBTN;

    final int MAX_ANSWERS = 6;
    QcmEditorController controller = new QcmEditorController();

    private List<CheckBox> correctAnswerCB = new ArrayList<>();
    private List<TextField> textFields = new ArrayList<>();

    @FXML
    public void initialize() {
        for(int i= 0; i < 4; i++) {
            answersContainer.getChildren().add(createAnswerRow());
        }
    }

    @FXML
    private void addNewAnswer() {
            answersContainer.getChildren().add(createAnswerRow());
            setAddAnswerBTNVisibility();
    }

    boolean checkMaxAnswers(){
        if (answersContainer.getChildren().size() >= MAX_ANSWERS){
            return true;
        }
        else return false;
    }

    void setAddAnswerBTNVisibility(){
        if(checkMaxAnswers()){
            addAnswerBTN.setDisable(true);
        }
        else {
            addAnswerBTN.setDisable(false);
        }
    }


    @FXML
    private void createQuestion() {
        try{
            String title = getTitle();
            List<String> answers = getAnswers();
            List<String> correcAnswers = getCorrectAnswers();
            controller.createQcm(title, answers, correcAnswers);
        } catch (Exception e){
            showErrorPopup(e.getMessage());
        }
    }

    /**
     * Crée et configure une ligne d'interface graphique représentant une réponse possible pour un QCM.
     * <p>
     * Cette ligne ({@link HBox}) contient :
     * <ul>
     * <li>Une {@link CheckBox} pour définir si cette réponse est la bonne.</li>
     * <li>Un {@link TextField} pour saisir le texte de la réponse.</li>
     * <li>Un {@link Button} "Supprimer" permettant de retirer cette réponse de la liste.</li>
     * </ul>
     * <p>
     * <b>Effets de bord :</b> L'appel à cette méthode ajoute automatiquement la CheckBox
     * et le TextField générés aux listes de suivi internes ({@code correctAnswerCB} et {@code textFields}).
     * <p>
     * <b>Comportement de suppression :</b> Lors du clic sur le bouton "Supprimer", la ligne est retirée
     * du conteneur parent ({@code answersContainer}) et les éléments sont retirés des listes de suivi.
     * Si le conteneur possède 2 réponses ou moins, la suppression est bloquée et une {@link RuntimeException} est levée.
     *
     * @return un objet {@link HBox} contenant les éléments visuels de la nouvelle réponse, prêt à être ajouté à la vue.
     */
    private HBox createAnswerRow() {
        HBox answerRow = new HBox();
        answerRow.setAlignment(Pos.CENTER_LEFT);
        answerRow.setPadding(new Insets(5, 10, 5, 10));

        CheckBox validAnswerCB = new CheckBox();

        TextField answerTF = new TextField();
        HBox.setHgrow(answerTF, Priority.ALWAYS);

        Button deleteBTN = new Button("Supprimer");

        correctAnswerCB.add(validAnswerCB);
        textFields.add(answerTF);


        deleteBTN.setOnAction(e -> {
            if (answersContainer.getChildren().size() > 2){
                answersContainer.getChildren().remove(answerRow);
                correctAnswerCB.remove(validAnswerCB);
                textFields.remove(answerTF);
                setAddAnswerBTNVisibility();
            }
            else try {
                throw new Exception("vous devez avoir au minimum 2 réponses");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        answerRow.getChildren().addAll(validAnswerCB, answerTF, deleteBTN);
        return answerRow;
    }

    // creer une exception personnalisee et afficher le message en pop-up
    List<String> getAnswers() throws Exception {
        List<String> answers = new ArrayList<>();
        for(TextField field : textFields) {
            if (field.getText().isEmpty()) throw new Exception("Un reponse ne peut pas etre vide");
            answers.add(field.getText());
        }
        return answers;
    }

    List<String> getCorrectAnswers() throws Exception {
        List<String> correctAnswers = new ArrayList<>();
        for(int i = 0; i < correctAnswerCB.size(); i++) {
            if(correctAnswerCB.get(i).isSelected()) {
                correctAnswers.add(textFields.get(i).getText());
            }
        }
        if (correctAnswers.isEmpty()) throw new Exception("il faut choisir au moins une reponse valide");
        return correctAnswers;
    }

    String getTitle() throws Exception {
        if (questionTitle.getText().isEmpty()) throw new Exception("il ne faut pas que la question posée soit vide");
        else return questionTitle.getText();
    }

    private void showErrorPopup(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Impossible de sauvegarder la question");
        alert.setContentText(message);
        alert.showAndWait(); // Met l'application en pause jusqu'à ce que l'utilisateur clique sur OK
    }
}
