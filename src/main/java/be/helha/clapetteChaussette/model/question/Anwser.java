package be.helha.clapetteChaussette.model.question;

public class Anwser {
    String anwser;
    boolean isCorrect;

    public Anwser(String anwser, boolean isCorrect) {
        this.anwser = anwser;
        this.isCorrect = isCorrect;
    }

    public String getAnwser() {
        return anwser;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
