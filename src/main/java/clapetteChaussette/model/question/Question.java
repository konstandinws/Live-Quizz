package clapetteChaussette.model.question;
import java.util.ArrayList;

public   abstract  class Question {
    protected String question;
    protected ArrayList<String> answers;
     boolean hasTimer ;


     public Question(String question) {
          this.question = question;
          this.hasTimer = false;
          this.answers = new ArrayList<>();
     }

     public String getQuestion() {
          return question;
     }
     public ArrayList<String> getAnswers() {
          return answers;
     }
}
