package com.devtec.gestion_stages.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjetStageResponse(
    Long id,
    String titre,
    LocalDate dateDebut,
    LocalDate dateFin,
    String objectifs,
    Long mentorId,
    List<Long> niveauxEtudesIds,
    List<CompetenceProjetResponse> competencesProjet
) {
    public record CompetenceProjetResponse(
        Long id,
        Long competenceId,
        Double poids
    ) {}
}