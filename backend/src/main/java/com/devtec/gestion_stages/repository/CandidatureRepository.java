package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.Candidature;
import com.devtec.gestion_stages.entity.enums.StatutCandidature;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByStatut(StatutCandidature statut);

    List<Candidature> findByProjetStageId(Long projetStageId);

    List<Candidature> findByNiveauEtudesId(Long niveauEtudesId);

    List<Candidature> findByEmail(String email);
}
