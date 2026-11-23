package com.devtec.gestion_stages.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
