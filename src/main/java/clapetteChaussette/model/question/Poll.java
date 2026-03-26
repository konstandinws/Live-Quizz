package clapetteChaussette.model.question;


import java.util.ArrayList;
import java.util.List;

public class Poll extends Question {


    private ArrayList<Integer> voteCounts;

    public Poll(String Question) {
        super(Question);
        this.voteCounts = new ArrayList<>();

    }

    public void addOption(String option) {
        this.answers.add(option);
        this.voteCounts.add(0);
    }
}