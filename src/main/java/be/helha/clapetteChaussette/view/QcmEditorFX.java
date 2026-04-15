package be.helha.clapetteChaussette.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.controller.editor.QcmEditorController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;

/**
 * Contrôleur JavaFX de l'interface d'édition de questions QCM.
 * <p>
 * Gère l'affichage dynamique des lignes de réponses et collecte les données
 * saisies par l'utilisateur avant de les transmettre à
 * {@link QcmEditorController}.
 * La validation métier est déléguée au controller ; cette classe ne gère
 * que la validation visuelle (affichage des erreurs).
 */
public class QcmEditorFX extends ViewFX {
    @FXML
    private Button saveQuestionBTN;

    @FXML
    private VBox answersContainer;
    @FXML
    private TextField questionTitle;
    @FXML
    private Button addAnswerBTN;

    /** Le nombre maximum de réponses autorisées pour un QCM. */
    final int MAX_ANSWERS = 6;
    /** Le nombre minimum de réponses requises pour un QCM. */
    final int MIN_ANSWERS = 2;

    QcmEditorController controller;

    private List<CheckBox> correctAnswerCB = new ArrayList<>();
    private List<TextField> textFields = new ArrayList<>();

    /**
     * Méthode appelée automatiquement par JavaFX après le chargement du fichier
     * FXML.
     * Initialise la vue avec 4 champs de réponses par défaut.
     */
    @FXML
    public void initialize() {
        for (int i = 0; i < 4; i++) {
            answersContainer.getChildren().add(createAnswerRow());
        }
    }

    /**
     * Ajoute une nouvelle ligne de réponse dans {@code answersContainer}.
     * Désactive le bouton d'ajout si le nombre maximum de réponses est atteint.
     * Lié au bouton "+" dans le FXML.
     */
    @FXML
    private void addNewAnswer() {
        answersContainer.getChildren().add(createAnswerRow());
        setAddAnswerBTNVisibility();
    }

    /**
     * Vérifie si le nombre maximum de réponses autorisées est atteint.
     *
     * @return {@code true} si {@code answersContainer} contient
     *         {@value MAX_ANSWERS} réponses ou plus
     */
    boolean checkMaxAnswers() {
        return answersContainer.getChildren().size() >= MAX_ANSWERS;
    }

    /**
     * Active ou désactive le bouton d'ajout de réponse selon
     * {@link #checkMaxAnswers()}.
     */
    void setAddAnswerBTNVisibility() {
        addAnswerBTN.setDisable(checkMaxAnswers());
    }

    /**
     * Collecte les données de la vue et demande au controller de créer la question.
     * Affiche une popup d'erreur si la validation échoue.
     * Lié au bouton "Sauvegarder" dans le FXML.
     */
    @FXML
    private void createQuestion() {
        try {
            String title = getTitle();
            List<String> answers = getAnswers();
            List<String> correctAnswers = getCorrectAnswers();
            controller.createQcm(title, answers, correctAnswers);
        } catch (InvalidQuestionException e) {
            showErrorPopup(e.getMessage());
        }
    }

    /**
     * Crée et configure une ligne représentant une réponse possible pour le QCM.
     * <p>
     * La ligne contient une {@link CheckBox} pour marquer la réponse comme
     * correcte,
     * un {@link TextField} pour saisir le texte, et un bouton "Supprimer".
     * <p>
     * La {@link CheckBox} et le {@link TextField} sont automatiquement ajoutés
     * aux listes internes {@code correctAnswerCB} et {@code textFields}.
     * <p>
     * La suppression est bloquée et une popup est affichée si le nombre de réponses
     * est inférieur ou égal à {@value MIN_ANSWERS}.
     *
     * @return un {@link HBox} prêt à être ajouté à {@code answersContainer}
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
            if (answersContainer.getChildren().size() > MIN_ANSWERS) {
                answersContainer.getChildren().remove(answerRow);
                correctAnswerCB.remove(validAnswerCB);
                textFields.remove(answerTF);
                setAddAnswerBTNVisibility();
            } else {
                showErrorPopup("Vous devez avoir au minimum 2 réponses.");
            }
        });
        answerRow.getChildren().addAll(validAnswerCB, answerTF, deleteBTN);
        return answerRow;
    }

    /**
     * Retourne le texte saisi dans chaque champ de réponse.
     *
     * @return la liste des textes de toutes les réponses, dans l'ordre d'affichage
     */
    List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        for (TextField field : textFields) {
            answers.add(field.getText());
        }
        return answers;
    }

    /**
     * Retourne le texte des réponses dont la {@link CheckBox} est cochée.
     *
     * @return la liste des textes des réponses marquées comme correctes
     */
    List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        for (int i = 0; i < correctAnswerCB.size(); i++) {
            if (correctAnswerCB.get(i).isSelected()) {
                correctAnswers.add(textFields.get(i).getText());
            }
        }
        return correctAnswers;
    }

    /**
     * Retourne le texte saisi dans le champ titre de la question.
     *
     * @return le titre de la question, peut être vide
     */
    String getTitle() {
        return questionTitle.getText();
    }

    /**
     * Affiche une popup d'erreur modale avec le message fourni.
     * L'application est mise en pause jusqu'à la fermeture de la popup.
     *
     * @param message le message d'erreur à afficher à l'utilisateur
     */
    private void showErrorPopup(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Impossible de sauvegarder la question");
        alert.setContentText(message);
        alert.showAndWait(); // Met l'application en pause jusqu'à ce que l'utilisateur clique sur OK
    }

    @Override
    protected String getFXMLPath() {
        return "QcmEditor.fxml";
    }
}
