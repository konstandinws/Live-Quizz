package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class QcmController implements IQuestionListener<String>, IViewController {
    @Override
    public void validate(String answer, Question qcm) {
        qcm.setAnswer(answer);
    }
}