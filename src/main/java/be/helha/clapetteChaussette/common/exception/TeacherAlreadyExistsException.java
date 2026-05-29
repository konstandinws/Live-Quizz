package be.helha.clapetteChaussette.common.exception;

public class TeacherAlreadyExistsException extends RuntimeException {
    public TeacherAlreadyExistsException(String name) {
        super("Teacher already exists : " + name);
    }
}
