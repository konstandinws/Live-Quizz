package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class OpenController extends BaseController implements IQuestionListener<String> {
    private OpenQuestion question;

    // Le constructeur doit maintenant recevoir NavigationService
    public OpenController(Question question, NavigationService navigationService) {
        // Appel obligatoire au constructeur parent en PREMIER
        super(navigationService);
        this.question = (OpenQuestion) question;
    }

    @Override
    public void validate(String answers) {

        question.setResponse(answers);
    }
}