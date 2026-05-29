package be.helha.clapetteChaussette.client.service.question;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.questions.Question;

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

    public QuestionDTO transformQuestionToDTO(Question question) {
        return new QuestionDTO(
                question.getQuuid(),
                question.getQuestionnaireUuid(),
                question.getQuestion(),
                question.getAnswers(),
                question.getType());
    }
}
