package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.CompteRequest;
import com.devtec.gestion_stages.dto.CompteResponse;
import com.devtec.gestion_stages.entity.AgentRH;
import com.devtec.gestion_stages.entity.Compte;
import com.devtec.gestion_stages.entity.Mentor;
import com.devtec.gestion_stages.exception.BusinessException;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.AgentRHRepository;
import com.devtec.gestion_stages.repository.CompteRepository;
import com.devtec.gestion_stages.repository.MentorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CompteService {

    private final CompteRepository compteRepository;
    private final MentorRepository mentorRepository;
    private final AgentRHRepository agentRHRepository;
    private final ModelMapper modelMapper;

    public CompteService(CompteRepository compteRepository,
                          MentorRepository mentorRepository,
                          AgentRHRepository agentRHRepository,
                          ModelMapper modelMapper) {
        this.compteRepository = compteRepository;
        this.mentorRepository = mentorRepository;
        this.agentRHRepository = agentRHRepository;
        this.modelMapper = modelMapper;
    }

    public CompteResponse createCompte(CompteRequest request) {
        log.info("Creating compte with login: {}", request.login());
        if (compteRepository.findByLogin(request.login()).isPresent()) {
            throw new BusinessException("Login already exists: " + request.login());
        }

        Compte compte = new Compte();
        compte.setLogin(request.login());
        compte.setMdp(request.mdp());

        if ("MENTOR".equalsIgnoreCase(request.role())) {
            Mentor mentor = new Mentor();
            mentor.setNom("Default Mentor");
            mentorRepository.save(mentor);
            compte.setMentor(mentor);
        } else if ("AGENT_RH".equalsIgnoreCase(request.role())) {
            AgentRH agentRH = new AgentRH();
            agentRH.setNom("Default Agent RH");
            agentRHRepository.save(agentRH);
            compte.setAgentRH(agentRH);
        }

        Compte saved = compteRepository.save(compte);
        log.info("Compte created with id: {}", saved.getId());
        return modelMapper.map(saved, CompteResponse.class);
    }

    @Transactional(readOnly = true)
    public Optional<CompteResponse> findByLogin(String login) {
        log.debug("Finding compte by login: {}", login);
        return compteRepository.findByLogin(login).map(compte -> modelMapper.map(compte, CompteResponse.class));
    }

    @Transactional(readOnly = true)
        public Optional<Compte> findEntityByLogin(String login) {
            log.debug("Finding compte entity by login: {}", login);
            return compteRepository.findByLogin(login);
        }

        @Transactional(readOnly = true)
    public Optional<CompteResponse> findById(Long id) {
        log.debug("Finding compte by id: {}", id);
        return compteRepository.findById(id).map(compte -> modelMapper.map(compte, CompteResponse.class));
    }

    public void deleteCompte(Long id) {
        log.info("Deleting compte with id: {}", id);
        Compte compte = compteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Compte not found with id: " + id));
        compteRepository.delete(compte);
    }
}