package be.helha.clapetteChaussette.common.models.events;

/**
 * Sent by a student who wants to join a session using its invitation code.
 */
public class JoinSessionRequest extends NetworkEvent {

    private final String code;
    private final String pseudo;

    public JoinSessionRequest(String code, String pseudo) {
        this.code = code;
        this.pseudo = pseudo;
    }

    public String getCode() {
        return code;
    }

    public String getPseudo() {
        return pseudo;
    }
}
