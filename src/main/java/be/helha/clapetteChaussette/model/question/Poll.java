package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class Poll extends Question {

    private final List<Integer> voteCounts;

    public Poll(String question) {
        super(question);
        this.voteCounts = new ArrayList<>();
    }

    public Poll(String Question, List<String> options) {
        this(Question);
        for (String option : options) {
            addOption(option);
        }
    }

    public void addOption(String option) {
        this.answers.add(option);
        this.voteCounts.add(0);
    }

    public void vote(int index) {
        this.voteCounts.set(index, this.voteCounts.get(index) + 1);
    }

    public List<Integer> getVoteCounts() {
        return this.voteCounts;
    }

    public String getChoice(int index) {
        return this.answers.get(index);
    }

    @Override
    public String toString() {
        return "Poll:" + this.getQuestion();
    }
}
