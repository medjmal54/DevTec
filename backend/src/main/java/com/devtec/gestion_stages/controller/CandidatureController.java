package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.CandidatureRequest;
import com.devtec.gestion_stages.dto.CandidatureResponse;
import com.devtec.gestion_stages.entity.enums.StatutCandidature;
import com.devtec.gestion_stages.service.CandidatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    private final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @PostMapping
    public ResponseEntity<CandidatureResponse> createCandidature(@RequestBody CandidatureRequest request) {
        CandidatureResponse candidature = candidatureService.createCandidature(request);
        return new ResponseEntity<>(candidature, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CandidatureResponse>> findAll() {
        List<CandidatureResponse> candidatures = candidatureService.findAll();
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CandidatureResponse>> findByStatut(@PathVariable StatutCandidature statut) {
        List<CandidatureResponse> candidatures = candidatureService.findByStatut(statut);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/projet/{projetStageId}")
    public ResponseEntity<List<CandidatureResponse>> findByProjetStageId(@PathVariable Long projetStageId) {
        List<CandidatureResponse> candidatures = candidatureService.findByProjetStageId(projetStageId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/niveau/{niveauEtudesId}")
    public ResponseEntity<List<CandidatureResponse>> findByNiveauEtudesId(@PathVariable Long niveauEtudesId) {
        List<CandidatureResponse> candidatures = candidatureService.findByNiveauEtudesId(niveauEtudesId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatureResponse> findById(@PathVariable Long id) {
        Optional<CandidatureResponse> candidature = candidatureService.findById(id);
        return candidature.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatureResponse> updateCandidature(@PathVariable Long id, @RequestBody CandidatureRequest request) {
        CandidatureResponse candidature = candidatureService.updateCandidature(id, request);
        return ResponseEntity.ok(candidature);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/accepter")
    public ResponseEntity<CandidatureResponse> accepterCandidature(@PathVariable Long id) {
        CandidatureResponse candidature = candidatureService.accepterCandidature(id);
        return ResponseEntity.ok(candidature);
    }

    @PatchMapping("/{id}/refuser")
    public ResponseEntity<CandidatureResponse> refuserCandidature(@PathVariable Long id) {
        CandidatureResponse candidature = candidatureService.refuserCandidature(id);
        return ResponseEntity.ok(candidature);
    }
}