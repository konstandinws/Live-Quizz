package be.helha.clapetteChaussette.client.view.question;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.StudentQuestionViewFX.IStudentQuestionListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;

public abstract class StudentQuestionViewFX extends ViewFX<IStudentQuestionListener> {

    protected final QuestionDTO question;

    public StudentQuestionViewFX(QuestionDTO question) {
        this.question = question;
    }

    public abstract void lockAfterAnswer();

    public interface IStudentQuestionListener extends IListener {
        void onAnswerSelected(int index);

        void onAnswerSelected(String answer);
    }
}
