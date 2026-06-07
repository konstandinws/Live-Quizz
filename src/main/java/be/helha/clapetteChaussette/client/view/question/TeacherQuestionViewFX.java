package be.helha.clapetteChaussette.client.view.question;

import java.util.List;

import be.helha.clapetteChaussette.client.view.IListener;
import be.helha.clapetteChaussette.client.view.ViewFX;
import be.helha.clapetteChaussette.client.view.question.TeacherQuestionViewFX.ITeacherViewListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;

public abstract class TeacherQuestionViewFX extends ViewFX<ITeacherViewListener> {

    protected final QuestionDTO question;
    protected int participantCount;
    protected int answeredCount = 0;

    public TeacherQuestionViewFX(QuestionDTO question, int participantCount) {
        this.question = question;
        this.participantCount = participantCount;
    }

    /** Updates the answered/total progress shown at the bottom (e.g. "3/5"). */
    public void setVoteProgress(int answered) {
        this.answeredCount = answered;
        refreshProgress();
    }

    /** Updates the answered/total progress shown at the bottom (e.g. "3/5"). */
    public void setVoteProgress(int answered, int total) {
        this.answeredCount = answered;
        this.participantCount = total;
        refreshProgress();
    }

    /** Updates the total number of participants (the denominator of N/N). */
    public void setParticipantCount(int count) {
        this.participantCount = count;
        refreshProgress();
    }

    public QuestionType getQuestionType() {
        return question.type();
    }

    protected abstract void refreshProgress();

    public abstract void setVotes(List<Integer> votes);

    public abstract void addAnswer(List<String> answerText);

    public abstract interface ITeacherViewListener extends IListener {
        void onNextRequested();
    }
}
