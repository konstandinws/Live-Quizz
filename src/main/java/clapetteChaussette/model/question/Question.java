package clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public abstract class Question {
     String question;
     List<String> answers;
     boolean hasTimer;


     public Question(String question) {
          this.question = question;
          this.hasTimer = false;
          this.answers = new ArrayList<>();
     }

     public String getQuestion() {
          return question;
     }

     public List<String> getAnswers() {
          return answers;
     }
}
