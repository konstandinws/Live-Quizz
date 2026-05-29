package be.helha.clapetteChaussette.common.models;

import java.util.UUID;

/**
 * Domain model representing a Teacher within the application.
 */
public class Teacher {
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
