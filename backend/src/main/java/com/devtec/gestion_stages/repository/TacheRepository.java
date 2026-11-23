package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.Tache;

public interface TacheRepository extends JpaRepository<Tache, Long> {

    List<Tache> findByProjetStageId(Long projetStageId);
}
