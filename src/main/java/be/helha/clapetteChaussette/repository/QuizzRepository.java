package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuizzDTO;

import java.util.List;
import java.util.UUID;

public interface QuizzRepository {
    void save(UUID uuid, UUID teacherUuid, String name);

    List<QuizzDTO> findAll(UUID teacherUuid);
}
