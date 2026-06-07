package be.helha.clapetteChaussette.common.models.events;

/**
 * Server reply to a {@link JoinSessionRequest}.
 */
public class JoinSessionResponse extends NetworkEvent {

    private final boolean success;
    private final int participantCount;
    private final String message;

    public JoinSessionResponse(boolean success, int participantCount, String message) {
        this.success = success;
        this.participantCount = participantCount;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getMessage() {
        return message;
    }
}
