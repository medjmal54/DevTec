package com.devtec.gestion_stages.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjetStageRequest(
    String titre,
    LocalDate dateDebut,
    LocalDate dateFin,
    String objectifs,
    Long mentorId,
    List<Long> niveauxEtudesIds,
    List<CompetenceProjetItem> competencesProjet
) {
    public record CompetenceProjetItem(
        Long competenceId,
        Double poids
    ) {}
}