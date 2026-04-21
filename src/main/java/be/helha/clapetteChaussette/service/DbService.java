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
    private TeacherRepository tRepo;
    private QuizzRepository qRepo;
    private QuestionRepository questionRepo;

    public DbService(TeacherRepository tRepo, QuizzRepository qRepo, QuestionRepository questionRepo) {
        this.tRepo = tRepo;
        this.qRepo = qRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public void saveTeacher(TeacherDTO teacher) {
        tRepo.save(teacher);
    }

    @Override
    public void saveQuizz(QuizzDTO quizz) {
        qRepo.save(quizz.uuid(), quizz.teacher_uuid(), quizz.name());
    }

    @Override
    public void saveQuestion(QuestionDTO question) {
        questionRepo.save(question);
    }

    @Override
    public void saveQuestions(List<QuestionDTO> questions) {
        for (QuestionDTO question : questions) {
            saveQuestion(question);
        }
    }

}
