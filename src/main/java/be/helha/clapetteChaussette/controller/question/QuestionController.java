package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.question.QuestionService;
import be.helha.clapetteChaussette.view.question.McqViewFX.IMcqListener;
import be.helha.clapetteChaussette.view.question.OpenQuestionFX.IOpenListener;
import be.helha.clapetteChaussette.view.question.PollViewFX.IPollListener;

public class QuestionController extends BaseController implements IOpenListener, IMcqListener, IPollListener {
    private Question question;
    private QuestionService questionService;

    public QuestionController(NavigationService navigationService, Question question, QuestionService questionService) {
        super(navigationService);
        this.question = question;
        this.questionService = questionService;
    }

    public void updateQuestion(Question newQuestion) {
        this.question = newQuestion;
    }

    // #region IQuestionListener methods
    @Override
    public void onMcqAnswerSelected(int index) {
        // TODO Auto-generated method stub
    }
    // #endregion

    // #region IOpenListener methods
    @Override
    public void onOpenAnswerSelected(String answer) {
        // TODO Auto-generated method stub

    }
    // #endregion

    // #region IPollListener methods
    @Override
    public void onPollAnswerSelected(String answer) {
        // TODO Auto-generated method stub

    }
    // #endregion
}
