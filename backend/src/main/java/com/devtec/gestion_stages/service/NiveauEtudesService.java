package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.NiveauEtudesRequest;
import com.devtec.gestion_stages.dto.NiveauEtudesResponse;
import com.devtec.gestion_stages.entity.NiveauEtudes;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.NiveauEtudesRepository;
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
public class NiveauEtudesService {

    private final NiveauEtudesRepository niveauEtudesRepository;
    private final ModelMapper modelMapper;

    public NiveauEtudesService(NiveauEtudesRepository niveauEtudesRepository, ModelMapper modelMapper) {
        this.niveauEtudesRepository = niveauEtudesRepository;
        this.modelMapper = modelMapper;
    }

    public NiveauEtudesResponse createNiveauEtudes(NiveauEtudesRequest request) {
        log.info("Creating Niveau Etudes: {}", request.niveau());
        NiveauEtudes niveauEtudes = new NiveauEtudes();
        niveauEtudes.setNiveau(request.niveau());
        NiveauEtudes saved = niveauEtudesRepository.save(niveauEtudes);
        log.info("Niveau Etudes created with id: {}", saved.getId());
        return modelMapper.map(saved, NiveauEtudesResponse.class);
    }

    @Transactional(readOnly = true)
    public List<NiveauEtudesResponse> findAll() {
        log.debug("Finding all Niveaux Etudes");
        return niveauEtudesRepository.findAll().stream()
            .map(niveau -> modelMapper.map(niveau, NiveauEtudesResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<NiveauEtudesResponse> findById(Long id) {
        log.debug("Finding Niveau Etudes by id: {}", id);
        return niveauEtudesRepository.findById(id).map(niveau -> modelMapper.map(niveau, NiveauEtudesResponse.class));
    }

    @Transactional(readOnly = true)
    public Optional<NiveauEtudesResponse> findByNiveau(String niveau) {
        log.debug("Finding Niveau Etudes by niveau: {}", niveau);
        return niveauEtudesRepository.findByNiveau(niveau).stream().findFirst()
            .map(niveauEtudes -> modelMapper.map(niveauEtudes, NiveauEtudesResponse.class));
    }

    public NiveauEtudesResponse updateNiveauEtudes(Long id, NiveauEtudesRequest request) {
        log.info("Updating Niveau Etudes with id: {}", id);
        NiveauEtudes niveauEtudes = niveauEtudesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + id));
        niveauEtudes.setNiveau(request.niveau());
        NiveauEtudes saved = niveauEtudesRepository.save(niveauEtudes);
        log.info("Niveau Etudes updated with id: {}", saved.getId());
        return modelMapper.map(saved, NiveauEtudesResponse.class);
    }

    public void deleteNiveauEtudes(Long id) {
        log.info("Deleting Niveau Etudes with id: {}", id);
        NiveauEtudes niveauEtudes = niveauEtudesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + id));
        niveauEtudesRepository.delete(niveauEtudes);
    }
}