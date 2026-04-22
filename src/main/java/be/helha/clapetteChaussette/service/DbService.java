// be.helha.clapetteChaussette.service.DbService
package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.IRepository;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<TeacherDTO> findTeacher(Credentials cred) {
        return tRepo.find(cred);
    }

    @Override
    public void saveQuizz(QuizzDTO quizz) {
        qRepo.save(quizz.uuid(), quizz.teacher_uuid(), quizz.name());
    }

    @Override
    public Optional<List<QuizzDTO>> findAllQuizzes(UUID teacherUuid) {
        return qRepo.findAll(teacherUuid);
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