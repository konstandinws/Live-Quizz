package be.helha.clapetteChaussette.repository;

import java.util.List;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;

public interface IRepository {
    public void saveTeacher(TeacherDTO teacher);

    public void saveQuizz(QuizzDTO quizz);

    public void saveQuestion(QuestionDTO question);

    public void saveQuestions(List<QuestionDTO> questions);
}
