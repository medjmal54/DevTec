package com.devtec.gestion_stages.dto;

import com.devtec.gestion_stages.entity.enums.StatutCandidature;
import java.time.LocalDate;
import java.util.List;

public record CandidatureRequest(
    String nomCandidat,
    String email,
    LocalDate dateCandidature,
    StatutCandidature statut,
    Long projetStageId,
    Long niveauEtudesId,
    List<Long> competenceIds
) {}