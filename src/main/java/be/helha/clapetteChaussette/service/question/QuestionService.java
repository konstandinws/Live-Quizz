package be.helha.clapetteChaussette.service.question;

import be.helha.clapetteChaussette.model.question.Question;

public class QuestionService {
    public boolean isMcqCorrect(Question question, int selectedIndex) {
        return question.getAnswers().stream()
                .filter(answer -> answer.isCorrect())
                .anyMatch(answer -> question.getAnswers().indexOf(answer) == selectedIndex);
        // this will stop and return true as soon as it finds a correct answer,
        // otherwise it will return false if no correct answer is found
    }

    public boolean isReponseCorrect(Question question, String answer) {
        return question.getAnswers().stream()
                .filter(ans -> ans.isCorrect())
                .anyMatch(ans -> ans.getAnswer().equals(answer));
    }
}
