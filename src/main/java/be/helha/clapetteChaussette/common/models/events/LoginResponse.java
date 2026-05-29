package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.Teacher;

import java.util.List;

public class LoginResponse extends NetworkEvent {

    private final boolean success;
    private final Teacher teacher;
    private final List<QuizzDTO> quizzes;
    private final String message;

    public LoginResponse(boolean success, Teacher teacher, List<QuizzDTO> quizzes, String message) {
        this.success = success;
        this.teacher = teacher;
        this.quizzes = quizzes;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<QuizzDTO> getQuizzes() {
        return quizzes;
    }

    public String getMessage() {
        return message;
    }
}
