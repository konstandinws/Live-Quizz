package be.helha.clapetteChaussette.common.exception;

public class TeacherAlreadyExistsException extends Exception {
    public TeacherAlreadyExistsException(String name) {
        super("Teacher already exists : " + name);
    }
}
