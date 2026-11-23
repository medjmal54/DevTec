package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.CompetenceProjet;

public interface CompetenceProjetRepository extends JpaRepository<CompetenceProjet, Long> {

    List<CompetenceProjet> findByProjetStageId(Long projetStageId);

    List<CompetenceProjet> findByCompetenceId(Long competenceId);
}
