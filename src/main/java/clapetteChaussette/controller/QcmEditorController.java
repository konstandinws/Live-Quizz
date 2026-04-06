package clapetteChaussette.controller;

import clapetteChaussette.model.question.QcmQuestion;

import java.util.List;

public class QcmEditorController {
    public QcmQuestion createQcm(String title, List<String> answers, List<String> correctAnswers) {
        if (correctAnswers.size() == 1){
            String correctAnswer = correctAnswers.getFirst();
            QcmQuestion qcm = new QcmQuestion(title, answers, correctAnswer);
            System.out.println("qcm creer -> QCM { %s - %s - %s}".formatted(title, answers, correctAnswer));
            return qcm;
        }
        else {
            return new QcmQuestion(title, answers, correctAnswers);
        }
    }
}
