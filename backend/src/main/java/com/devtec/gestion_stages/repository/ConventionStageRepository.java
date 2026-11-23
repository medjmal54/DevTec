package com.devtec.gestion_stages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.ConventionStage;

public interface ConventionStageRepository extends JpaRepository<ConventionStage, Long> {

    Optional<ConventionStage> findByNumero(String numero);
}
