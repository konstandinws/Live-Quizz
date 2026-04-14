package be.helha.clapetteChaussette.model.dto;

import java.util.UUID;

// cette classe stocke uniquement les données importantes pour le questionnaire
// elle ne contient pas tous les questions et reponses de ces derniers
// je pense c'est mieux car pour l'affichage on envoie le titre du questionnaire et ensuite une fois lancer on peut
// envoyer les questions une à une ou par batch au réseau
public record QuizzDTO(UUID uuid, String nom) {}
