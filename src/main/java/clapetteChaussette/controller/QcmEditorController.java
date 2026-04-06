package clapetteChaussette.controller;

import clapetteChaussette.exception.InvalidQuestionException;
import clapetteChaussette.model.question.QcmQuestion;

import java.util.List;

public class QcmEditorController {

    // faire communiquer le service pour qu'il enregistre dans la db
    // acutellement le return ne sert a rien car il est appelé par la vue et elle ne peut creer d'objet je suppose a remplacer par service
    public QcmQuestion createQcm(String title, List<String> answers, List<String> correctAnswers) throws InvalidQuestionException {

        validateTitle(title);
        validateAnswers(answers);
        validateCorrectAnswer(correctAnswers);

        if (correctAnswers.size() == 1){
            String correctAnswer = correctAnswers.getFirst();
            QcmQuestion qcm = new QcmQuestion(title, answers, correctAnswer);
            System.out.printf("qcm creer -> QCM { %s - %s - %s}%n", title, answers, correctAnswer);
            return qcm;
        }
        else {
            return new QcmQuestion(title, answers, correctAnswers);
        }
    }

    private void validateTitle(String title) throws InvalidQuestionException {
        if (title.isBlank()) throw new InvalidQuestionException("il ne faut pas que la question posée soit vide");
    }

    private void validateAnswers(List<String> answers) throws InvalidQuestionException {
        for(String answer : answers){
            if (answer.isBlank()) throw new InvalidQuestionException("une reponse ne peut pas etre vide");
        }
    }

    private void validateCorrectAnswer(List<String> correctAnswers) throws InvalidQuestionException {
        if (correctAnswers.isEmpty()) throw new InvalidQuestionException("il faut au moins une réponse correcte");
    }
}
