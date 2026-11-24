package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.ProjetStageRequest;
import com.devtec.gestion_stages.dto.ProjetStageResponse;
import com.devtec.gestion_stages.service.ProjetStageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projets-stage")
public class ProjetStageController {

    private final ProjetStageService projetStageService;

    public ProjetStageController(ProjetStageService projetStageService) {
        this.projetStageService = projetStageService;
    }

    @PostMapping
    public ResponseEntity<ProjetStageResponse> createProjetStage(@RequestBody ProjetStageRequest request) {
        ProjetStageResponse projetStage = projetStageService.createProjetStage(request);
        return new ResponseEntity<>(projetStage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjetStageResponse>> findAll() {
        List<ProjetStageResponse> projets = projetStageService.findAll();
        return ResponseEntity.ok(projets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetStageResponse> findById(@PathVariable Long id) {
        Optional<ProjetStageResponse> projetStage = projetStageService.findById(id);
        return projetStage.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<ProjetStageResponse>> findByMentorId(@PathVariable Long mentorId) {
        List<ProjetStageResponse> projets = projetStageService.findByMentorId(mentorId);
        return ResponseEntity.ok(projets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetStageResponse> updateProjetStage(@PathVariable Long id, @RequestBody ProjetStageRequest request) {
        ProjetStageResponse projetStage = projetStageService.updateProjetStage(id, request);
        return ResponseEntity.ok(projetStage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjetStage(@PathVariable Long id) {
        projetStageService.deleteProjetStage(id);
        return ResponseEntity.noContent().build();
    }
}