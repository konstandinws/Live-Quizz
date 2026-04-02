package clapetteChaussette.controller;

import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.IQuestionListener;

public class OpenController implements IQuestionListener<String> {
    @Override
    public boolean validate(String aws, Question question) {
        System.out.println("Réponse enregistré : " + aws);
        return true;
    }
}
