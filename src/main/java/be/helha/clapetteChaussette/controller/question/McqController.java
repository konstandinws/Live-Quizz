package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class McqController extends BaseController implements IQuestionListener<String> {
    private McqQuestion question;

    // Le constructeur doit maintenant recevoir le service de navigation
    public McqController(McqQuestion question, NavigationService navigationService) {
        super(navigationService); // Appel indispensable au constructeur parent
        this.question = question;
    }

    @Override
    public void validate(String answers) {
        // Ta logique actuelle
        question.setResponse(answers);
    }
}
