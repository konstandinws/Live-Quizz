package be.helha.clapetteChaussette.model.dto;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.List;
import java.util.UUID;

public record QuestionDTO (UUID uuid, UUID quuid, String titre, List<String> answers, List<String> correctAnswers, QuestionType type){}
