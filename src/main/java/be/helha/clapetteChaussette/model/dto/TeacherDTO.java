package be.helha.clapetteChaussette.model.dto;

import java.util.UUID;

public record TeacherDTO(UUID uuid, String name, String hashed_password) {}
