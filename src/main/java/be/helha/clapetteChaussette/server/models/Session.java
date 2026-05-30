package be.helha.clapetteChaussette.server.models;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A live quizz session created when a teacher launches a quizz.
 * <p>
 * It ties an invitation {@code code} to the launched {@link QuizzDTO} and to the
 * teacher's connection ({@code host}). Students who join with the code are added
 * to {@code clients}.
 * </p>
 */
public class Session {

    private final String code;
    private final QuizzDTO quizz;
    private final ClientThreadHandler host;
    private final List<ClientThreadHandler> clients = new ArrayList<>();

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

    public void addClient(ClientThreadHandler client) {
        clients.add(client);
    }

    public List<ClientThreadHandler> getClients() {
        return clients;
    }

    public int getNumberParticipants() {
        return clients.size();
    }
}
