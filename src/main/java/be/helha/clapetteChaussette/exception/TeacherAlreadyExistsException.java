package be.helha.clapetteChaussette.exception;

public class TeacherAlreadyExistsException extends RuntimeException {
    public TeacherAlreadyExistsException(String name) {
        super("Teacher already exists : " + name);
    }
}
