package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;

import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {
    void save(QuestionDTO question);

    Optional<QuestionDTO> findByUuid(UUID questionUuid);
}
