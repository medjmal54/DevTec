package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.TacheRequest;
import com.devtec.gestion_stages.dto.TacheResponse;
import com.devtec.gestion_stages.entity.ProjetStage;
import com.devtec.gestion_stages.entity.Tache;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.ProjetStageRepository;
import com.devtec.gestion_stages.repository.TacheRepository;
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
public class TacheService {

    private final TacheRepository tacheRepository;
    private final ProjetStageRepository projetStageRepository;
    private final ModelMapper modelMapper;

    public TacheService(TacheRepository tacheRepository,
                         ProjetStageRepository projetStageRepository,
                         ModelMapper modelMapper) {
        this.tacheRepository = tacheRepository;
        this.projetStageRepository = projetStageRepository;
        this.modelMapper = modelMapper;
    }

    public TacheResponse createTache(Long projetStageId, TacheRequest request) {
        log.info("Creating Tache for projetStageId: {}", projetStageId);
        ProjetStage projetStage = projetStageRepository.findById(projetStageId)
            .orElseThrow(() -> new ResourceNotFoundException("Projet Stage not found with id: " + projetStageId));

        Tache tache = new Tache();
        tache.setLibelle(request.libelle());
        tache.setProjetStage(projetStage);
        Tache saved = tacheRepository.save(tache);
        log.info("Tache created with id: {}", saved.getId());
        return modelMapper.map(saved, TacheResponse.class);
    }

    @Transactional(readOnly = true)
    public List<TacheResponse> findByProjetStageId(Long projetStageId) {
        log.debug("Finding Taches by projetStageId: {}", projetStageId);
        return tacheRepository.findByProjetStageId(projetStageId).stream()
            .map(tache -> modelMapper.map(tache, TacheResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<TacheResponse> findById(Long id) {
        log.debug("Finding Tache by id: {}", id);
        return tacheRepository.findById(id).map(tache -> modelMapper.map(tache, TacheResponse.class));
    }

    public TacheResponse updateTache(Long id, TacheRequest request) {
        log.info("Updating Tache with id: {}", id);
        Tache tache = tacheRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tache not found with id: " + id));
        tache.setLibelle(request.libelle());
        Tache saved = tacheRepository.save(tache);
        log.info("Tache updated with id: {}", saved.getId());
        return modelMapper.map(saved, TacheResponse.class);
    }

    public void deleteTache(Long id) {
        log.info("Deleting Tache with id: {}", id);
        Tache tache = tacheRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tache not found with id: " + id));
        tacheRepository.delete(tache);
    }
}