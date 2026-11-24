package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.TacheRequest;
import com.devtec.gestion_stages.dto.TacheResponse;
import com.devtec.gestion_stages.service.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taches")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @PostMapping("/projet/{projetStageId}")
    public ResponseEntity<TacheResponse> createTache(@PathVariable Long projetStageId, @RequestBody TacheRequest request) {
        TacheResponse tache = tacheService.createTache(projetStageId, request);
        return new ResponseEntity<>(tache, HttpStatus.CREATED);
    }

    @GetMapping("/projet/{projetStageId}")
    public ResponseEntity<List<TacheResponse>> findByProjetStageId(@PathVariable Long projetStageId) {
        List<TacheResponse> taches = tacheService.findByProjetStageId(projetStageId);
        return ResponseEntity.ok(taches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheResponse> findById(@PathVariable Long id) {
        Optional<TacheResponse> tache = tacheService.findById(id);
        return tache.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheResponse> updateTache(@PathVariable Long id, @RequestBody TacheRequest request) {
        TacheResponse tache = tacheService.updateTache(id, request);
        return ResponseEntity.ok(tache);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }
}