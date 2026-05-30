package be.helha.clapetteChaussette.common.models.events;

/**
 * Server reply to a {@link LaunchQuizzRequest}. Carries the invitation code the
 * teacher must share so students can join the session.
 */
public class LaunchQuizzResponse extends NetworkEvent {

    private final boolean success;
    private final String code;
    private final int participantCount;
    private final String message;

    public LaunchQuizzResponse(boolean success, String code, int participantCount, String message) {
        this.success = success;
        this.code = code;
        this.participantCount = participantCount;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getMessage() {
        return message;
    }
}
