package com.devtec.gestion_stages.dto;

import jakarta.validation.constraints.NotBlank;

public record CompteRequest(
    @NotBlank(message = "Login is required")
    String login,
    
    @NotBlank(message = "Password is required")
    String mdp,
    
    String role
) {}