package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.question.McqViewFX.IMcqListener;
import be.helha.clapetteChaussette.view.question.OpenQuestionFX.IOpenListener;
import be.helha.clapetteChaussette.view.question.PollViewFX.IPollListener;

public class QuestionController extends BaseController implements IOpenListener, IMcqListener, IPollListener {
    public QuestionController(NavigationService navigationService) {
        super(navigationService);
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
