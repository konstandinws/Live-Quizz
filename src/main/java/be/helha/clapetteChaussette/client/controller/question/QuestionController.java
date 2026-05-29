package be.helha.clapetteChaussette.client.controller.question;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.common.models.questions.Question;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.question.QuestionService;
import be.helha.clapetteChaussette.client.view.IQuestionView;
import be.helha.clapetteChaussette.client.view.question.McqViewFX.IMcqListener;
import be.helha.clapetteChaussette.client.view.question.OpenQuestionFX.IOpenListener;
import be.helha.clapetteChaussette.client.view.question.PollViewFX.IPollListener;

public class QuestionController extends BaseController implements IOpenListener, IMcqListener, IPollListener {
    private Question question;
    private QuestionService questionService;

    private IQuestionView view;

    public QuestionController(NavigationService navigationService, Question question, QuestionService questionService,
            IQuestionView view) {
        super(navigationService);
        this.question = question;
        this.questionService = questionService;
        this.view = view;
    }

    public void updateQuestion(Question newQuestion) {
        this.question = newQuestion;
    }

    // #region IQuestionListener methods
    @Override
    public void onMcqAnswerSelected(int index) {
        view.showWaiting();
        boolean isCorrect = questionService.isMcqCorrect(question, index);
        validateOnView(isCorrect);
    }
    // #endregion

    // #region IOpenListener methods
    @Override
    public void onOpenAnswerSelected(String answer) {
        view.showWaiting();
        boolean isCorrect = questionService.isReponseCorrect(question, answer);
        validateOnView(isCorrect);
    }
    // #endregion

    // #region IPollListener methods
    @Override
    public void onPollAnswerSelected(String answer) {
        view.showWaiting();
    }
    // #endregion

    private void validateOnView(boolean isCorrect) {
        if (isCorrect) {
            view.showCorrect();
        } else {
            view.showIncorrect();
        }
    }
}
