package be.helha.clapetteChaussette.common.models.events;

/**
 * Sent by a student who wants to join a session using its invitation code.
 */
public class JoinSessionRequest extends NetworkEvent {

    private final String code;

    public JoinSessionRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
