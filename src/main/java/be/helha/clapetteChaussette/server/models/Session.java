package be.helha.clapetteChaussette.server.models;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A live quizz session created when a teacher launches a quizz.
 * <p>
 * It ties an invitation {@code code} to the launched {@link QuizzDTO} and to
 * the
 * teacher's connection ({@code host}). Students who join with the code are
 * added
 * to {@code clients}.
 * </p>
 */
public class Session {

    private final String code;
    private final QuizzDTO quizz;
    private final ClientThreadHandler host;
    private final List<ClientThreadHandler> clients = new ArrayList<>();
    private final List<String> participantPseudos = new ArrayList<>();

    /** The quizz's questions, loaded once when the session starts. */
    private List<QuestionDTO> questions = new ArrayList<>();
    /** Index of the question currently shown; -1 before the quizz starts. */
    private int currentIndex = -1;
    /** Who answered the current question, and which answer index they chose. */
    private final Map<ClientThreadHandler, Integer> answersForCurrent = new HashMap<>();
    private final Map<ClientThreadHandler, String> answerTextForCurrent = new HashMap<>();

    public Session(String code, QuizzDTO quizz, ClientThreadHandler host) {
        this.code = code;
        this.quizz = quizz;
        this.host = host;
    }

    public String getCode() {
        return code;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public ClientThreadHandler getHost() {
        return host;
    }

    public void addClient(ClientThreadHandler client, String pseudo) {
        clients.add(client);
        participantPseudos.add(pseudo);
    }

    public void removeClient(ClientThreadHandler client) {
        int index = clients.indexOf(client);
        if (index != -1) {
            clients.remove(index);
            participantPseudos.remove(index);
        }
    }

    public List<ClientThreadHandler> getClients() {
        return clients;
    }

    public List<String> getParticipantPseudos() {
        return new ArrayList<>(participantPseudos);
    }

    public int getNumberParticipants() {
        return clients.size();
    }

    /** Loads the quizz's questions and resets the progression to the start. */
    public void loadQuestions(List<QuestionDTO> questions) {
        this.questions = new ArrayList<>(questions);
        this.currentIndex = -1;
    }

    /**
     * Advances to the next question.
     *
     * @return the next {@link QuestionDTO}, or empty if the quizz is finished.
     */
    public Optional<QuestionDTO> nextQuestion() {
        if (currentIndex + 1 >= questions.size()) {
            return Optional.empty();
        }
        currentIndex++;
        answersForCurrent.clear(); // a new question starts with no answers
        answerTextForCurrent.clear();
        System.out.println(questions.get(currentIndex).answers().get(0));
        return Optional.of(questions.get(currentIndex));
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * The question currently shown, or empty before the quizz starts / once
     * finished.
     */
    public Optional<QuestionDTO> getCurrentQuestion() {
        if (currentIndex < 0 || currentIndex >= questions.size()) {
            return Optional.empty();
        }
        return Optional.of(questions.get(currentIndex));
    }

    /**
     * Records a student's answer to the current question (overwrites a previous
     * one).
     */
    public void recordAnswer(ClientThreadHandler client, int answerIndex) {
        answersForCurrent.put(client, answerIndex);
    }

    public void recordAnswer(ClientThreadHandler client, String answerText) {
        answerTextForCurrent.put(client, answerText);
    }

    /**
     * Records a student's text answer to the current question (overwrites a
     * previous
     * one).
     */
    public void recordTextAnswer(ClientThreadHandler client, String answerText) {
        answerTextForCurrent.put(client, answerText);
    }

    /** How many participants have answered the current question. */
    public int getAnsweredCount() {
        if (questions.get(currentIndex).type() == QuestionType.OPEN) {
            return answerTextForCurrent.size();
        } else {
            return answersForCurrent.size();
        }
    }

    /**
     * The number of votes per answer for the current question, in answer order
     * (index 0 = first answer, etc.). Recomputed from the recorded answers, so a
     * student changing their mind is reflected correctly.
     */
    public List<Integer> getVoteTotal() {
        Optional<QuestionDTO> current = getCurrentQuestion();
        if (current.isEmpty()) {
            return new ArrayList<>();
        }

        int answerCount = current.get().answers().size();
        int[] counts = new int[answerCount];
        for (int index : answersForCurrent.values()) {
            if (index >= 0 && index < answerCount) {
                counts[index]++;
            }
        }

        List<Integer> tally = new ArrayList<>();
        for (int count : counts) {
            tally.add(count);
        }
        return tally;
    }

    public List<String> getTextAnswerTotal() {
        return new ArrayList<>(answerTextForCurrent.values());
    }
}
