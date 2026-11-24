package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.CandidatureRequest;
import com.devtec.gestion_stages.dto.CandidatureResponse;
import com.devtec.gestion_stages.entity.Candidature;
import com.devtec.gestion_stages.entity.Competence;
import com.devtec.gestion_stages.entity.NiveauEtudes;
import com.devtec.gestion_stages.entity.ProjetStage;
import com.devtec.gestion_stages.entity.enums.StatutCandidature;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.CandidatureRepository;
import com.devtec.gestion_stages.repository.CompetenceRepository;
import com.devtec.gestion_stages.repository.NiveauEtudesRepository;
import com.devtec.gestion_stages.repository.ProjetStageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final ProjetStageRepository projetStageRepository;
    private final NiveauEtudesRepository niveauEtudesRepository;
    private final CompetenceRepository competenceRepository;
    private final ModelMapper modelMapper;

    public CandidatureService(CandidatureRepository candidatureRepository,
                               ProjetStageRepository projetStageRepository,
                               NiveauEtudesRepository niveauEtudesRepository,
                               CompetenceRepository competenceRepository,
                               ModelMapper modelMapper) {
        this.candidatureRepository = candidatureRepository;
        this.projetStageRepository = projetStageRepository;
        this.niveauEtudesRepository = niveauEtudesRepository;
        this.competenceRepository = competenceRepository;
        this.modelMapper = modelMapper;
    }

    public CandidatureResponse createCandidature(CandidatureRequest request) {
        log.info("Creating Candidature for: {}", request.nomCandidat());
        ProjetStage projetStage = projetStageRepository.findById(request.projetStageId())
            .orElseThrow(() -> new ResourceNotFoundException("Projet Stage not found with id: " + request.projetStageId()));

        NiveauEtudes niveauEtudes = niveauEtudesRepository.findById(request.niveauEtudesId())
            .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + request.niveauEtudesId()));

        Candidature candidature = new Candidature();
        candidature.setNomCandidat(request.nomCandidat());
        candidature.setEmail(request.email());
        candidature.setDateCandidature(request.dateCandidature());
        candidature.setStatut(request.statut() != null ? request.statut() : StatutCandidature.EnAttente);
        candidature.setProjetStage(projetStage);
        candidature.setNiveauEtudes(niveauEtudes);

        List<Competence> competences = new ArrayList<>();
        for (Long competenceId : request.competenceIds()) {
            Competence competence = competenceRepository.findById(competenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + competenceId));
            competences.add(competence);
        }
        candidature.setCompetences(competences);

        Candidature saved = candidatureRepository.save(candidature);
        log.info("Candidature created with id: {}", saved.getId());
        return modelMapper.map(saved, CandidatureResponse.class);
    }

    @Transactional(readOnly = true)
    public List<CandidatureResponse> findAll() {
        log.debug("Finding all Candidatures");
        return candidatureRepository.findAll().stream()
            .map(candidature -> modelMapper.map(candidature, CandidatureResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidatureResponse> findByStatut(StatutCandidature statut) {
        log.debug("Finding Candidatures by statut: {}", statut);
        return candidatureRepository.findByStatut(statut).stream()
            .map(candidature -> modelMapper.map(candidature, CandidatureResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidatureResponse> findByProjetStageId(Long projetStageId) {
        log.debug("Finding Candidatures by projetStageId: {}", projetStageId);
        return candidatureRepository.findByProjetStageId(projetStageId).stream()
            .map(candidature -> modelMapper.map(candidature, CandidatureResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidatureResponse> findByNiveauEtudesId(Long niveauEtudesId) {
        log.debug("Finding Candidatures by niveauEtudesId: {}", niveauEtudesId);
        return candidatureRepository.findByNiveauEtudesId(niveauEtudesId).stream()
            .map(candidature -> modelMapper.map(candidature, CandidatureResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CandidatureResponse> findById(Long id) {
        log.debug("Finding Candidature by id: {}", id);
        return candidatureRepository.findById(id).map(candidature -> modelMapper.map(candidature, CandidatureResponse.class));
    }

    public CandidatureResponse updateCandidature(Long id, CandidatureRequest request) {
        log.info("Updating Candidature with id: {}", id);
        Candidature candidature = candidatureRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature not found with id: " + id));

        ProjetStage projetStage = projetStageRepository.findById(request.projetStageId())
            .orElseThrow(() -> new ResourceNotFoundException("Projet Stage not found with id: " + request.projetStageId()));

        NiveauEtudes niveauEtudes = niveauEtudesRepository.findById(request.niveauEtudesId())
            .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + request.niveauEtudesId()));

        candidature.setNomCandidat(request.nomCandidat());
        candidature.setEmail(request.email());
        candidature.setDateCandidature(request.dateCandidature());
        candidature.setStatut(request.statut() != null ? request.statut() : StatutCandidature.EnAttente);
        candidature.setProjetStage(projetStage);
        candidature.setNiveauEtudes(niveauEtudes);

        candidature.getCompetences().clear();
        for (Long competenceId : request.competenceIds()) {
            Competence competence = competenceRepository.findById(competenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + competenceId));
            candidature.getCompetences().add(competence);
        }

        Candidature saved = candidatureRepository.save(candidature);
        log.info("Candidature updated with id: {}", saved.getId());
        return modelMapper.map(saved, CandidatureResponse.class);
    }

    public void deleteCandidature(Long id) {
        log.info("Deleting Candidature with id: {}", id);
        Candidature candidature = candidatureRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature not found with id: " + id));
        candidatureRepository.delete(candidature);
    }

    public CandidatureResponse accepterCandidature(Long id) {
        log.info("Accepter Candidature with id: {}", id);
        Candidature candidature = candidatureRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature not found with id: " + id));
        candidature.setStatut(StatutCandidature.Acceptee);
        Candidature saved = candidatureRepository.save(candidature);
        log.info("Candidature accepted with id: {}", saved.getId());
        return modelMapper.map(saved, CandidatureResponse.class);
    }

    public CandidatureResponse refuserCandidature(Long id) {
        log.info("Refuser Candidature with id: {}", id);
        Candidature candidature = candidatureRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature not found with id: " + id));
        candidature.setStatut(StatutCandidature.Refusee);
        Candidature saved = candidatureRepository.save(candidature);
        log.info("Candidature refused with id: {}", saved.getId());
        return modelMapper.map(saved, CandidatureResponse.class);
    }
}