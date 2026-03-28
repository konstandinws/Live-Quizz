package clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.Random;
public class QuestionLibre extends Question {
 ArrayList<String> randomQuestion;
 public QuestionLibre(String question) {
     super(question);
        this.randomQuestion= new ArrayList<>();
 }
 public void fillQuestion(){
     randomQuestion.add("En java définit une interface fonctionelle");
     randomQuestion.add("Définit une classe abstraite");
     randomQuestion.add("Comment tu trouves Brahim pphysiquement");
 }
 public String choiseQuestion(){
     if(this.randomQuestion.isEmpty()){
         System.out.println("Aucune question pour le moments");
     }
     Random rand = new Random();
     int number= rand.nextInt(this.randomQuestion.size());
     return this.randomQuestion.get(number);

 }
}
