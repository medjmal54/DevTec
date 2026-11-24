package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.AgentRHRequest;
import com.devtec.gestion_stages.dto.AgentRHResponse;
import com.devtec.gestion_stages.entity.AgentRH;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.AgentRHRepository;
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
public class AgentRHService {

    private final AgentRHRepository agentRHRepository;
    private final ModelMapper modelMapper;

    public AgentRHService(AgentRHRepository agentRHRepository, ModelMapper modelMapper) {
        this.agentRHRepository = agentRHRepository;
        this.modelMapper = modelMapper;
    }

    public AgentRHResponse createAgentRH(AgentRHRequest request) {
        log.info("Creating Agent RH: {}", request.nom());
        AgentRH agentRH = new AgentRH();
        agentRH.setNom(request.nom());
        AgentRH saved = agentRHRepository.save(agentRH);
        log.info("Agent RH created with id: {}", saved.getId());
        return modelMapper.map(saved, AgentRHResponse.class);
    }

    @Transactional(readOnly = true)
    public List<AgentRHResponse> findAll() {
        log.debug("Finding all Agent RH");
        return agentRHRepository.findAll().stream()
            .map(agentRH -> modelMapper.map(agentRH, AgentRHResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AgentRHResponse> findById(Long id) {
        log.debug("Finding Agent RH by id: {}", id);
        return agentRHRepository.findById(id).map(agentRH -> modelMapper.map(agentRH, AgentRHResponse.class));
    }

    public AgentRHResponse updateAgentRH(Long id, AgentRHRequest request) {
        log.info("Updating Agent RH with id: {}", id);
        AgentRH agentRH = agentRHRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Agent RH not found with id: " + id));
        agentRH.setNom(request.nom());
        AgentRH saved = agentRHRepository.save(agentRH);
        log.info("Agent RH updated with id: {}", saved.getId());
        return modelMapper.map(saved, AgentRHResponse.class);
    }

    public void deleteAgentRH(Long id) {
        log.info("Deleting Agent RH with id: {}", id);
        AgentRH agentRH = agentRHRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Agent RH not found with id: " + id));
        agentRHRepository.delete(agentRH);
    }
}