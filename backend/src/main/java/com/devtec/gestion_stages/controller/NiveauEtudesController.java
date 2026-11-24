package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.NiveauEtudesRequest;
import com.devtec.gestion_stages.dto.NiveauEtudesResponse;
import com.devtec.gestion_stages.service.NiveauEtudesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/niveaux-etudes")
public class NiveauEtudesController {

    private final NiveauEtudesService niveauEtudesService;

    public NiveauEtudesController(NiveauEtudesService niveauEtudesService) {
        this.niveauEtudesService = niveauEtudesService;
    }

    @PostMapping
    public ResponseEntity<NiveauEtudesResponse> createNiveauEtudes(@RequestBody NiveauEtudesRequest request) {
        NiveauEtudesResponse niveau = niveauEtudesService.createNiveauEtudes(request);
        return new ResponseEntity<>(niveau, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NiveauEtudesResponse>> findAll() {
        List<NiveauEtudesResponse> niveaux = niveauEtudesService.findAll();
        return ResponseEntity.ok(niveaux);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NiveauEtudesResponse> findById(@PathVariable Long id) {
        Optional<NiveauEtudesResponse> niveau = niveauEtudesService.findById(id);
        return niveau.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<NiveauEtudesResponse> findByNiveau(@PathVariable String niveau) {
        Optional<NiveauEtudesResponse> result = niveauEtudesService.findByNiveau(niveau);
        return result.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NiveauEtudesResponse> updateNiveauEtudes(@PathVariable Long id, @RequestBody NiveauEtudesRequest request) {
        NiveauEtudesResponse niveau = niveauEtudesService.updateNiveauEtudes(id, request);
        return ResponseEntity.ok(niveau);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveauEtudes(@PathVariable Long id) {
        niveauEtudesService.deleteNiveauEtudes(id);
        return ResponseEntity.noContent().build();
    }
}