package com.devtec.gestion_stages.dto;

import java.time.LocalDate;
import java.util.List;

public record TacheResponse(
    Long id,
    String libelle,
    Long projetStageId
) {}