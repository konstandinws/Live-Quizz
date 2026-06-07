package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.models.Teacher;

public class RegisterRequest extends NetworkEvent{

    private final Teacher teacher;
    private final String password;

    public RegisterRequest(Teacher teacher, String password) {
        this.teacher = teacher;
        this.password = password;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getPassword() {
        return password;
    }
}
