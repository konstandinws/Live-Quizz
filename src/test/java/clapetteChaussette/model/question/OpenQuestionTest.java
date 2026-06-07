package clapetteChaussette.model.question;

import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.OpenQuestion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class OpenQuestionTest {

    @Test
    public void testCreationQuestionOuverte() {
        // Préparation des données
        String titre = "Quelle est la capitale de la France ?";
        List<Answer> reponses = Arrays.asList(new Answer("Paris", true), new Answer("paris", true));
        boolean avecPoints = true;

        // on crée l'objet
        OpenQuestion q = new OpenQuestion(titre, reponses, avecPoints);

        // 3. Vérification : est-ce que l'objet a bien enregistré les infos ?
        assertEquals(titre, q.getQuestion(), "Le titre devrait être correct");
        assertEquals(reponses, q.getAnswers(), "Les réponses devraient être enregistrées");
        assertTrue(q.isPointsEnabled(), "Les points devraient être activés");
    }
}
