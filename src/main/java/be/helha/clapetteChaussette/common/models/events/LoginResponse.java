package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.models.Teacher;

public class LoginResponse extends NetworkEvent {

    private final boolean success;
    private final Teacher teacher;
    private final String message;

    public LoginResponse(boolean success, Teacher teacher, String message) {
        this.success = success;
        this.teacher = teacher;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getMessage() {
        return message;
    }
}
