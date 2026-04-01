package clapetteChaussette.controller;

import java.util.ArrayList;
import java.util.List;

import clapetteChaussette.model.question.Poll;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.OpenQuestion;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements IMainListener {

    @Override
    public void onQcmLaunch() {
        String question = "que vaut 12 + 5 ?";
        List<String> answers = new ArrayList<>();
        answers.add("4");
        answers.add("12");
        answers.add("15");
        answers.add("17");
        String correctAnswer = "17";
        QcmQuestion qcm = new QcmQuestion(question, answers, correctAnswer);
        try {
            afficherQcm(qcm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPollLaunch() {
        String question = "Quelle est votre couleur préférée ?";
        Question poll = new Poll(question, List.of("Rouge", "Bleu", "Vert", "Jaune"));
        try {
            afficherPoll(poll);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFreeLaunch() {
        OpenQuestion qst = new OpenQuestion("comment tu trouves brahim physiquement ?");
        try{
            afficherFree(qst);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Free launched");
        // launch new view
    }

    public void afficherQcm(Question question) throws Exception {
        // creer la question avec le service normalement
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/qcm.fxml"));
        IQcmListener viewController = new QcmController();
        Parent root = loader.load();
        QcmViewFX qcmViewFX = loader.getController();
        qcmViewFX.set(viewController, (QcmQuestion) question);
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question QCM");
        nouvelleFenetre.setScene(new Scene(root, 600, 400));
        nouvelleFenetre.show();
    }

    public void afficherPoll(Question question) throws Exception {
        // creer la question avec le service normalement
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clapetteChaussette/fxml/poll.fxml"));
        IPoolListener viewController = new PollController();
        Parent root = loader.load();
        PollViewFX pollViewFX = loader.getController();
        pollViewFX.set(viewController, (Poll) question);
        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question Poll");
        nouvelleFenetre.setScene(new Scene(root, 600, 400));
        nouvelleFenetre.show();
    }

    public void afficherFree(OpenQuestion question) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clapetteChaussette/fxml/questionfree.fxml"));
        IFreeListener viewController = new FreeController();
        Parent root = loader.load();
        QuestionFreeViewFX freeViewFx = loader.getController();
        freeViewFx.set(viewController, question);

        Stage nouvelleFenetre = new Stage();
        nouvelleFenetre.setTitle("Question Free");
        nouvelleFenetre.setScene(new Scene(root, 600, 400));
        nouvelleFenetre.show();
    }
}