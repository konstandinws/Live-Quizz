package be.helha.clapetteChaussette.client.view.teacher;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX.ITeacherQuizzChoicesListener;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class TeacherQuizzChoicesViewFX extends ViewFX<ITeacherQuizzChoicesListener> {

    private final List<QuizzDTO> quizzes;
    private final String teacherName;

    @FXML
    private Button newQuizzBTN;

    @FXML
    private Button returnBTN;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox quizzVbox;

    public TeacherQuizzChoicesViewFX(String teacherName, List<QuizzDTO> quizzes) {
        this.teacherName = teacherName;
        this.quizzes = quizzes;
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(teacherName);
        displayQuizzes();
    }

    @FXML
    private void handleNewQuizz() {
        listener.onNewQuizzRequested();
    }

    public void setQuizzes(List<QuizzDTO> quizz) {
        this.quizzes.clear();
        this.quizzes.addAll(quizz);
        displayQuizzes();
    }

    /**
     * Populates the quizz list with the teacher's questionnaires.
     *
     * @param quizzes the quizzes to display (may be empty).
     */
    public void displayQuizzes() {
        quizzVbox.getChildren().clear();
        quizzVbox.setSpacing(10);
        quizzVbox.setPadding(new Insets(10));
        if (quizzes == null || quizzes.isEmpty()) {
            quizzVbox.getChildren().add(new Label("Aucun questionnaire pour le moment."));
            return;
        }
        for (QuizzDTO quizz : quizzes) {
            quizzVbox.getChildren().add(createQuizzRow(quizz));
        }
    }

    /**
     * Builds a rectangle for a quizz: its name on the left and a button on the
     * right that launches the quizz and navigates to the waiting screen.
     *
     * @param quizz the quizz to display.
     * @return the styled row representing the quizz.
     */
    private HBox createQuizzRow(QuizzDTO quizz) {
        Label nameLabel = new Label(quizz.name());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button launchBtn = new Button("Lancer");
        launchBtn.setOnAction(e -> {
            try {
                listener.onLaunchQuizzRequested(quizz);
            } catch (IOException ex) {
                showError("Erreur lors de la tentative de lancement du quiz : " + ex.getMessage());
            }
        });

        HBox row = new HBox(10, nameLabel, spacer, launchBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;"
                + " -fx-border-color: #c0c0c0; -fx-border-radius: 5;");
        return row;
    }

    @Override
    protected String getFXMLPath() {
        return "teacherQuizzChoices.fxml";
    }

    public interface ITeacherQuizzChoicesListener extends IListener {
        void onNewQuizzRequested();

        void onLaunchQuizzRequested(QuizzDTO quizz) throws IOException;
    }
}
