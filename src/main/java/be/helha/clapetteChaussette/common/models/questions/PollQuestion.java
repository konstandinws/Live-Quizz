package be.helha.clapetteChaussette.common.models.questions;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PollQuestion extends Question {

    private final List<Integer> voteCounts;

    public PollQuestion(String question) {
        super(question, QuestionType.POLL);
        this.voteCounts = new ArrayList<Integer>();
    }

    public PollQuestion(String question, List<Answer> options) {
        this(question);
        for (Answer option : options) {
            addOption(option);
        }
    }

    public void addOption(Answer option) {
        this.answers.add(option);
        this.voteCounts.add(0);
    }

    public void vote(int index) {
        this.voteCounts.set(index, this.voteCounts.get(index) + 1);
    }

    public List<Integer> getVoteCounts() {
        return Collections.unmodifiableList(this.voteCounts);
    }

    public String getChoice(int index) {
        return this.answers.get(index).getAnswer();
    }

    @Override
    public String toString() {
        return "Poll:" + this.getQuestion();
    }
}
