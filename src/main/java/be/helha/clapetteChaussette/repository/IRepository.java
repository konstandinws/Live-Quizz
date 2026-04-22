// be.helha.clapetteChaussette.repository.IRepository
package be.helha.clapetteChaussette.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;

public interface IRepository {
    void saveTeacher(TeacherDTO teacher);

    Optional<TeacherDTO> findTeacher(Credentials cred); // Nouveau

    void saveQuizz(QuizzDTO quizz);

    Optional<List<QuizzDTO>> findAllQuizzes(UUID teacherUuid); // Nouveau

    void saveQuestion(QuestionDTO question);

    void saveQuestions(List<QuestionDTO> questions);
}