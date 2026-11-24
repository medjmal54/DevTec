package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.CompteRequest;
import com.devtec.gestion_stages.dto.CompteResponse;
import com.devtec.gestion_stages.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PostMapping
    public ResponseEntity<CompteResponse> createCompte(@RequestBody CompteRequest request) {
        CompteResponse compte = compteService.createCompte(request);
        return new ResponseEntity<>(compte, HttpStatus.CREATED);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<CompteResponse> findByLogin(@PathVariable String login) {
        Optional<CompteResponse> compte = compteService.findByLogin(login);
        return compte.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteResponse> findById(@PathVariable Long id) {
        Optional<CompteResponse> compte = compteService.findById(id);
        return compte.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }
}