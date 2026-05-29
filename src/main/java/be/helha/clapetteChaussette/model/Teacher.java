package be.helha.clapetteChaussette.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Domain model representing a Teacher within the application.
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String name;

    /**
     * Constructs a new Teacher instance.
     *
     * @param uuid The unique identifier for the teacher.
     * @param name The name of the teacher.
     */
    public Teacher(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() { return uuid;}

    public String getName() { return name;}
}
