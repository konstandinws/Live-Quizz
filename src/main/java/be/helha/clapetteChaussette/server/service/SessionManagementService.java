package be.helha.clapetteChaussette.server.service;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.utils.CodeGenerator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service responsible for session management.
 * Handles creating sessions, joining/leaving, and retrieving session
 * information.
 */
public class SessionManagementService {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * Creates a new session with a unique invitation code.
     *
     * @param quizz the quizz to launch
     * @param host  the teacher's client handler
     * @return the newly created session
     */
    public Session createSession(QuizzDTO quizz, ClientThreadHandler host) {
        String code = generateUniqueCode();
        Session session = new Session(code, quizz, host);
        sessions.put(code, session);
        return session;
    }

    /**
     * Retrieves a session by its invitation code.
     *
     * @param code the invitation code
     * @return an Optional containing the session if found
     */
    public Optional<Session> getSessionByCode(String code) {
        return Optional.ofNullable(sessions.get(code));
    }

    /**
     * Retrieves the session that a teacher (host) is managing.
     *
     * @param host the teacher's client handler
     * @return an Optional containing the session if found
     */
    public Optional<Session> getSessionByHost(ClientThreadHandler host) {
        return sessions.values().stream()
                .filter(session -> session.getHost() == host)
                .findFirst();
    }

    /**
     * Retrieves the session that a student (client) belongs to.
     *
     * @param client the student's client handler
     * @return an Optional containing the session if found
     */
    public Optional<Session> getSessionByClient(ClientThreadHandler client) {
        return sessions.values().stream()
                .filter(session -> session.getClients().contains(client))
                .findFirst();
    }

    /**
     * Adds a student to a session.
     *
     * @param code   the session's invitation code
     * @param client the student's client handler
     * @param pseudo the student's pseudonym
     * @return true if the student was added successfully
     */
    public boolean addClientToSession(String code, ClientThreadHandler client, String pseudo) {
        Optional<Session> sessionOpt = getSessionByCode(code);
        if (sessionOpt.isEmpty()) {
            return false;
        }

        Session session = sessionOpt.get();
        session.addClient(client, pseudo);
        return true;
    }

    /**
     * Removes a student from a session.
     *
     * @param client the student's client handler
     */
    public void removeClientFromSession(ClientThreadHandler client) {
        sessions.values().forEach(session -> {
            if (session.getClients().contains(client)) {
                session.removeClient(client);
            }
        });
    }

    /**
     * Removes a session (typically when the teacher disconnects).
     *
     * @param host the teacher's client handler
     */
    public void removeSessionByHost(ClientThreadHandler host) {
        sessions.values().removeIf(session -> session.getHost() == host);
    }

    /**
     * Generates a unique invitation code not already used by an active session.
     *
     * @return a unique invitation code
     */
    private String generateUniqueCode() {
        String code;
        do {
            code = CodeGenerator.generate();
        } while (sessions.containsKey(code));
        return code;
    }

    /**
     * Gets all active sessions (for debug or monitoring purposes).
     *
     * @return the map of all active sessions
     */
    public Map<String, Session> getAllSessions() {
        return sessions;
    }
}
