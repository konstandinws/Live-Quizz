package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.IRepository;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.repository.TeacherRepository;

import java.util.List;

public class DbService implements IRepository {

    @Override
    public void saveTeacher(TeacherDTO teacher) {
        TeacherRepository tRepo = new TeacherRepository();
        tRepo.save(teacher);
    }

    @Override
    public void saveQuizz(QuizzDTO quizz) {
        QuizzRepository quizzRepo = new QuizzRepository();
        quizzRepo.save(quizz.uuid(), quizz.teacher_uuid(), quizz.name());
    }

    @Override
    public void saveQuestion(QuestionDTO question) {
        QuestionRepository qRepo = new QuestionRepository();
        qRepo.save(question);
    }

    @Override
    public void saveQuestions(List<QuestionDTO> questions) {
        for (QuestionDTO question : questions) {
            saveQuestion(question);
        }
    }

}
