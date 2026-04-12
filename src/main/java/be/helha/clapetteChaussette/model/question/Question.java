package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Question {
     UUID quuid;
     String question;
     List<String> answers;
     boolean hasTimer;
     String answer;
     QuestionType type;

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

     public void setAnswer(String answer) {
          this.answer = answer;
     }

     public UUID getQuuid() {return quuid;}

     public QuestionType getType() {return type;}
}
