package be.helha.clapetteChaussette.model.dao;

import java.util.UUID;

public record TeacherDAO(UUID uuid, String name, String hashed_password) {}
