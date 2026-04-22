package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizzService {

    QuizzRepository quizzRepository;
    QuestionRepository questionRepository;

    public QuizzService(QuizzRepository quizzRepository, QuestionRepository questionRepository){
        this.quizzRepository = quizzRepository;
        this.questionRepository = questionRepository;
    }

    public void registerNewQuizz(Quizz quizz, UUID teacherUuid) {

        // save the quizz in the database
        QuizzDTO quizzDTO = tranformQuizzInDTO(quizz, teacherUuid);
        quizzRepository.save(quizzDTO);

        // save all the questions of the quizz in the database
        List<QuestionDTO> questions = createQuestionDTO(quizz);

        for(QuestionDTO q : questions) {
            questionRepository.save(q);
        }
    }

    public Quizz createQuizz(String name) {
        return new Quizz(name);
    }

    private QuizzDTO tranformQuizzInDTO(Quizz quizz, UUID teacherUuid) {
        return new QuizzDTO(quizz.getUuid(), quizz.getName(), teacherUuid);
    }

    public List<QuizzDTO> findAllQuizzes(UUID teacher) {
        return quizzRepository.findAll(teacher);
    }

    public void addQuestion(Quizz quizz, Question question) {
        quizz.addQuestion(question);
    }

    public List<QuestionDTO> createQuestionDTO(Quizz quizz) {
        List<QuestionDTO> questionDTO = new ArrayList<>();
        for(Question q : quizz.getQuestions()) {
            questionDTO.add(new QuestionDTO(q.getQuuid(), quizz.getUuid(), q.getQuestion(), q.getAnswers(), q.getType()));
        }
        return questionDTO;
    }
}