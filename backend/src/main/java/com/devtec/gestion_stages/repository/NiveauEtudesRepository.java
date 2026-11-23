package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.NiveauEtudes;

public interface NiveauEtudesRepository extends JpaRepository<NiveauEtudes, Long> {

    List<NiveauEtudes> findByNiveau(String niveau);
}
