package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.CompetenceRequest;
import com.devtec.gestion_stages.dto.CompetenceResponse;
import com.devtec.gestion_stages.entity.Competence;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.CompetenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CompetenceService {

    private final CompetenceRepository competenceRepository;
    private final ModelMapper modelMapper;

    public CompetenceService(CompetenceRepository competenceRepository, ModelMapper modelMapper) {
        this.competenceRepository = competenceRepository;
        this.modelMapper = modelMapper;
    }

    public CompetenceResponse createCompetence(CompetenceRequest request) {
        log.info("Creating Competence: {}", request.libelle());
        Competence competence = new Competence();
        competence.setLibelle(request.libelle());
        Competence saved = competenceRepository.save(competence);
        log.info("Competence created with id: {}", saved.getId());
        return modelMapper.map(saved, CompetenceResponse.class);
    }

    @Transactional(readOnly = true)
    public List<CompetenceResponse> findAll() {
        log.debug("Finding all Competences");
        return competenceRepository.findAll().stream()
            .map(competence -> modelMapper.map(competence, CompetenceResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CompetenceResponse> findById(Long id) {
        log.debug("Finding Competence by id: {}", id);
        return competenceRepository.findById(id).map(competence -> modelMapper.map(competence, CompetenceResponse.class));
    }

    @Transactional(readOnly = true)
    public List<CompetenceResponse> findByLibelle(String libelle) {
        log.debug("Finding Competences by libelle: {}", libelle);
        return competenceRepository.findByLibelle(libelle).stream()
            .map(competence -> modelMapper.map(competence, CompetenceResponse.class))
            .collect(Collectors.toList());
    }

    public CompetenceResponse updateCompetence(Long id, CompetenceRequest request) {
        log.info("Updating Competence with id: {}", id);
        Competence competence = competenceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + id));
        competence.setLibelle(request.libelle());
        Competence saved = competenceRepository.save(competence);
        log.info("Competence updated with id: {}", saved.getId());
        return modelMapper.map(saved, CompetenceResponse.class);
    }

    public void deleteCompetence(Long id) {
        log.info("Deleting Competence with id: {}", id);
        Competence competence = competenceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + id));
        competenceRepository.delete(competence);
    }
}