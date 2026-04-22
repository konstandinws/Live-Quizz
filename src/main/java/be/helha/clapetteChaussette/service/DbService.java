// be.helha.clapetteChaussette.service.DbService
package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.repository.IRepository;
import be.helha.clapetteChaussette.repository.IQuestionRepository;
import be.helha.clapetteChaussette.repository.IQuizzRepository;
import be.helha.clapetteChaussette.repository.ITeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// deprecated Service, the controllers should use the dedicated services for each repository
public class DbService implements IRepository {
    private ITeacherRepository tRepo;
    private IQuizzRepository qRepo;
    private IQuestionRepository questionRepo;

    public DbService(ITeacherRepository tRepo, IQuizzRepository qRepo, IQuestionRepository questionRepo) {
        this.tRepo = tRepo;
        this.qRepo = qRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public void saveTeacher(TeacherDTO teacher) {
        tRepo.save(teacher);
    }

    @Override
    public Optional<TeacherDTO> findTeacher(String name) {
        return tRepo.findByName(name);
    }

    @Override
    public void saveQuizz(QuizzDTO quizz) {
        qRepo.save(quizz);
    }

    @Override
    public List<QuizzDTO> findAllQuizzes(UUID teacherUuid) {
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
