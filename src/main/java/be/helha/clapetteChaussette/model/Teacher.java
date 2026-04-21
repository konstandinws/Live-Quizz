package be.helha.clapetteChaussette.model;

import java.util.UUID;

public class Teacher {
    private UUID uuid;
    private String name;


    public Teacher(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() { return uuid;}

    public String getName() { return name;}
}
