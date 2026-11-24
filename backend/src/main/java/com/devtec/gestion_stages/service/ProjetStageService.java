package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.ProjetStageRequest;
import com.devtec.gestion_stages.dto.ProjetStageResponse;
import com.devtec.gestion_stages.entity.Competence;
import com.devtec.gestion_stages.entity.CompetenceProjet;
import com.devtec.gestion_stages.entity.Mentor;
import com.devtec.gestion_stages.entity.NiveauEtudes;
import com.devtec.gestion_stages.entity.ProjetStage;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.CompetenceProjetRepository;
import com.devtec.gestion_stages.repository.CompetenceRepository;
import com.devtec.gestion_stages.repository.MentorRepository;
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
public class ProjetStageService {

    private final ProjetStageRepository projetStageRepository;
    private final MentorRepository mentorRepository;
    private final NiveauEtudesRepository niveauEtudesRepository;
    private final CompetenceRepository competenceRepository;
    private final CompetenceProjetRepository competenceProjetRepository;
    private final ModelMapper modelMapper;

    public ProjetStageService(ProjetStageRepository projetStageRepository,
                               MentorRepository mentorRepository,
                               NiveauEtudesRepository niveauEtudesRepository,
                               CompetenceRepository competenceRepository,
                               CompetenceProjetRepository competenceProjetRepository,
                               ModelMapper modelMapper) {
        this.projetStageRepository = projetStageRepository;
        this.mentorRepository = mentorRepository;
        this.niveauEtudesRepository = niveauEtudesRepository;
        this.competenceRepository = competenceRepository;
        this.competenceProjetRepository = competenceProjetRepository;
        this.modelMapper = modelMapper;
    }

    public ProjetStageResponse createProjetStage(ProjetStageRequest request) {
        log.info("Creating Projet Stage: {}", request.titre());
        Mentor mentor = mentorRepository.findById(request.mentorId())
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found with id: " + request.mentorId()));

        ProjetStage projetStage = new ProjetStage();
        projetStage.setTitre(request.titre());
        projetStage.setDateDebut(request.dateDebut());
        projetStage.setDateFin(request.dateFin());
        projetStage.setObjectifs(request.objectifs());
        projetStage.setMentor(mentor);

        List<NiveauEtudes> niveauxEtudes = new ArrayList<>();
        for (Long niveauId : request.niveauxEtudesIds()) {
            NiveauEtudes niveau = niveauEtudesRepository.findById(niveauId)
                .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + niveauId));
            niveauxEtudes.add(niveau);
        }
        projetStage.setNiveauxEtudes(niveauxEtudes);

        List<CompetenceProjet> competencesProjet = new ArrayList<>();
        for (ProjetStageRequest.CompetenceProjetItem item : request.competencesProjet()) {
            Competence competence = competenceRepository.findById(item.competenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + item.competenceId()));
            
            CompetenceProjet cp = new CompetenceProjet();
            cp.setPoids(item.poids());
            cp.setProjetStage(projetStage);
            cp.setCompetence(competence);
            competencesProjet.add(cp);
        }
        projetStage.setCompetencesProjet(competencesProjet);

        ProjetStage saved = projetStageRepository.save(projetStage);
        log.info("Projet Stage created with id: {}", saved.getId());
        return modelMapper.map(saved, ProjetStageResponse.class);
    }

    @Transactional(readOnly = true)
    public List<ProjetStageResponse> findAll() {
        log.debug("Finding all Projet Stages");
        return projetStageRepository.findAll().stream()
            .map(projetStage -> modelMapper.map(projetStage, ProjetStageResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProjetStageResponse> findById(Long id) {
        log.debug("Finding Projet Stage by id: {}", id);
        return projetStageRepository.findById(id).map(projetStage -> modelMapper.map(projetStage, ProjetStageResponse.class));
    }

    @Transactional(readOnly = true)
    public List<ProjetStageResponse> findByMentorId(Long mentorId) {
        log.debug("Finding Projet Stages by mentorId: {}", mentorId);
        return projetStageRepository.findByMentorId(mentorId).stream()
            .map(projetStage -> modelMapper.map(projetStage, ProjetStageResponse.class))
            .collect(Collectors.toList());
    }

    public ProjetStageResponse updateProjetStage(Long id, ProjetStageRequest request) {
        log.info("Updating Projet Stage with id: {}", id);
        ProjetStage projetStage = projetStageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projet Stage not found with id: " + id));

        Mentor mentor = mentorRepository.findById(request.mentorId())
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found with id: " + request.mentorId()));

        projetStage.setTitre(request.titre());
        projetStage.setDateDebut(request.dateDebut());
        projetStage.setDateFin(request.dateFin());
        projetStage.setObjectifs(request.objectifs());
        projetStage.setMentor(mentor);

        List<NiveauEtudes> niveauxEtudes = new ArrayList<>();
        for (Long niveauId : request.niveauxEtudesIds()) {
            NiveauEtudes niveau = niveauEtudesRepository.findById(niveauId)
                .orElseThrow(() -> new ResourceNotFoundException("Niveau Etudes not found with id: " + niveauId));
            niveauxEtudes.add(niveau);
        }
        projetStage.setNiveauxEtudes(niveauxEtudes);

        projetStage.getCompetencesProjet().clear();
        for (ProjetStageRequest.CompetenceProjetItem item : request.competencesProjet()) {
            Competence competence = competenceRepository.findById(item.competenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Competence not found with id: " + item.competenceId()));
            
            CompetenceProjet cp = new CompetenceProjet();
            cp.setPoids(item.poids());
            cp.setProjetStage(projetStage);
            cp.setCompetence(competence);
            projetStage.getCompetencesProjet().add(cp);
        }

        ProjetStage saved = projetStageRepository.save(projetStage);
        log.info("Projet Stage updated with id: {}", saved.getId());
        return modelMapper.map(saved, ProjetStageResponse.class);
    }

    public void deleteProjetStage(Long id) {
        log.info("Deleting Projet Stage with id: {}", id);
        ProjetStage projetStage = projetStageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projet Stage not found with id: " + id));
        projetStageRepository.delete(projetStage);
    }
}