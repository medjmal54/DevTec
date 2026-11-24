package com.devtec.gestion_stages.dto;

import java.time.LocalDate;

public record CompteResponse(
    Long id,
    String login,
    String role
) {}