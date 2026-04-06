package clapetteChaussette.controller;

import clapetteChaussette.model.question.QcmQuestion;

import java.util.List;

public class QcmEditorController {

    public QcmQuestion createQcm(String title, List<String> answers, List<String> correctAnswers) throws Exception {

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

    private void validateTitle(String title) throws Exception {
        if (title.isEmpty()) throw new Exception("il ne faut pas que la question posée soit vide");
    }

    private void validateAnswers(List<String> answers) throws Exception {
        for(String answer : answers){
            if (answer.isBlank()) throw new Exception("une reponse ne peut pas etre vide");
        }
    }

    private void validateCorrectAnswer(List<String> correctAnswers) throws Exception {
        if (correctAnswers.isEmpty()) throw new Exception("il faut au moins une réponse correcte");
    }
}
