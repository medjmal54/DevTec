package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.Competence;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    List<Competence> findByLibelle(String libelle);
}
