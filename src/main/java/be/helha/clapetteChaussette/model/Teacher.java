package be.helha.clapetteChaussette.model;

import java.util.UUID;

public class Teacher {
    private UUID uuid;
    private String name;
    private String hashed_password;

    public Teacher(String name, String hashed_password) {
        uuid = UUID.randomUUID();
        this.name = name;
        this.hashed_password = hashed_password;
    }

    public UUID getUuid() { return uuid;}

    public String getName() { return name;}

    public String getHashed_password() { return hashed_password;}

}
