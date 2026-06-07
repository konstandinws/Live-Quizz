package be.helha.clapetteChaussette.common.models.questions;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Question {
     private UUID quuid;
     private UUID questionnaireUuid;
     private String question;
     List<Answer> answers;
     String response;
     boolean hasTimer;
     QuestionType type;

     public Question(String question, QuestionType type) {
          this.question = question;
          this.hasTimer = false;
          this.answers = new ArrayList<>();
          this.type = type;
     }

     public String getQuestion() {
          return question;
     }

     public List<Answer> getAnswers() {
          return Collections.unmodifiableList(answers);
     }

     public void setResponse(String answer) {
          this.response = answer;
     }

     public UUID getQuuid() {
          return quuid;
     }

     public UUID getQuestionnaireUuid() {
          return questionnaireUuid;
     }

     public QuestionType getType() {
          return type;
     };
}
