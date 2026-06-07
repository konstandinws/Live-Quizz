package be.helha.clapetteChaussette.client.view.question;

import java.util.List;

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

    /**
     * Reveals the result to the student once the teacher shows the answers.
     *
     * @param correctQuestion the question carrying the correct answers (the copy
     *                        received while answering had them hidden).
     * @param votes           the vote count per answer, in answer order.
     */
    public abstract void revealCorrectAnswers(QuestionDTO correctQuestion, List<Integer> votes);

    public interface IStudentQuestionListener extends IListener {
        void onAnswerSelected(int index);

        void onAnswerSelected(String answer);
    }
}
