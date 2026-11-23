package com.devtec.gestion_stages.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtec.gestion_stages.entity.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByLogin(String login);
}
