package clapetteChaussette.model.question;


import java.util.ArrayList;
import java.util.List;

public class Poll extends Question {

    private boolean isMultiplechoice;
    private ArrayList<Integer> voteCounts;

    public Poll(String Question,boolean isMultiplechoice) {
        super(Question);
        this.isMultiplechoice = isMultiplechoice;
        this.voteCounts = new ArrayList<>();

    }
    public void addOption(String option) {
     this.answers.add(option);
     this.voteCounts.add(0);
    }
    public boolean isMultiplechoice() {
        return isMultiplechoice;
    }
    public void setMultiplechoice(boolean multiplechoice) {
        isMultiplechoice = multiplechoice;
    }

    @Override
    public String toString() {
        return "Sondage:" + this.getQuestion() + "(Choix multiples:" + isMultiplechoice + ")";
    }
}
