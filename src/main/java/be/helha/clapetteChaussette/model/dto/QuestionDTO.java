package be.helha.clapetteChaussette.model.dto;

import be.helha.clapetteChaussette.model.enums.QuestionType;
import be.helha.clapetteChaussette.model.question.Anwser;

import java.util.List;
import java.util.UUID;

// Data Transfert Object pour faciliter l'ecriture dans la BD, ici la DB ne connais pas ce qu'on va lui passer comme question
// Elle stocke juste les elements importants et ceux utiles pour reconstruire la question
// il y a surement une meilleure maniere de faire mais celle la ne requiert pas la modification de la moitie du code existant
public record QuestionDTO(UUID uuid, UUID quizzUuid, String titre, List<Anwser> anwsers, QuestionType type) {
}
