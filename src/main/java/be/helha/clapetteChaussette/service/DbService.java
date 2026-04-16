package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.repository.TeacherRepository;

import java.util.List;

// Cette classe sert uniquement pour les tests vers la Database. Elle permet de voir si les
// commandes s'exécutent correctement
// Elle sera effacée une fois la DB finie

public class DbService {

    public void saveTeacher(TeacherDTO teacher) {
        TeacherRepository tRepo = new TeacherRepository();
        tRepo.save(teacher);
    }

    public void saveQuizz(QuizzDTO quizz) {
        QuizzRepository quizzRepo = new QuizzRepository();
        quizzRepo.save(quizz.uuid(), quizz.teacher_uuid(), quizz.name());
    }

    public void saveQuestion(QuestionDTO question) {
        QuestionRepository qRepo = new QuestionRepository();
        qRepo.save(question);
    }

    public void saveQuestions(List<QuestionDTO> questions) {
        for (QuestionDTO question : questions) {
            saveQuestion(question);
        }
    }

}
