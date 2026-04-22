package be.helha.clapetteChaussette.view;

public interface IQuestionListener<T> {
    void validate(T answer);
}
