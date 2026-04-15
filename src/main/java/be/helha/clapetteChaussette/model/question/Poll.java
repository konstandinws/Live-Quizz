package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poll extends Question {

    private final List<Integer> voteCounts;

    public Poll(String question) {
        super(question, QuestionType.POLL);
        this.voteCounts = new ArrayList<Integer>();
    }

    public Poll(String Question, List<Anwser> options) {
        this(Question);
        for (Anwser option : options) {
            addOption(option);
        }
    }

    public void addOption(Anwser option) {
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
        return this.answers.get(index).getAnwser();
    }

    @Override
    public String toString() {
        return "Poll:" + this.getQuestion();
    }
}
