package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.CompetenceRequest;
import com.devtec.gestion_stages.dto.CompetenceResponse;
import com.devtec.gestion_stages.service.CompetenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    @PostMapping
    public ResponseEntity<CompetenceResponse> createCompetence(@RequestBody CompetenceRequest request) {
        CompetenceResponse competence = competenceService.createCompetence(request);
        return new ResponseEntity<>(competence, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompetenceResponse>> findAll() {
        List<CompetenceResponse> competences = competenceService.findAll();
        return ResponseEntity.ok(competences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetenceResponse> findById(@PathVariable Long id) {
        Optional<CompetenceResponse> competence = competenceService.findById(id);
        return competence.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/libelle/{libelle}")
    public ResponseEntity<List<CompetenceResponse>> findByLibelle(@PathVariable String libelle) {
        List<CompetenceResponse> competences = competenceService.findByLibelle(libelle);
        return ResponseEntity.ok(competences);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetenceResponse> updateCompetence(@PathVariable Long id, @RequestBody CompetenceRequest request) {
        CompetenceResponse competence = competenceService.updateCompetence(id, request);
        return ResponseEntity.ok(competence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        competenceService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }
}