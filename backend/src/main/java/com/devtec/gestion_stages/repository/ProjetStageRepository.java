package com.devtec.gestion_stages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.ProjetStage;

public interface ProjetStageRepository extends JpaRepository<ProjetStage, Long> {

    List<ProjetStage> findByMentorId(Long mentorId);
}
