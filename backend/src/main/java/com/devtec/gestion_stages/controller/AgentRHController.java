package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.AgentRHRequest;
import com.devtec.gestion_stages.dto.AgentRHResponse;
import com.devtec.gestion_stages.service.AgentRHService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents-rh")
public class AgentRHController {

    private final AgentRHService agentRHService;

    public AgentRHController(AgentRHService agentRHService) {
        this.agentRHService = agentRHService;
    }

    @PostMapping
    public ResponseEntity<AgentRHResponse> createAgentRH(@RequestBody AgentRHRequest request) {
        AgentRHResponse agentRH = agentRHService.createAgentRH(request);
        return new ResponseEntity<>(agentRH, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgentRHResponse>> findAll() {
        List<AgentRHResponse> agents = agentRHService.findAll();
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentRHResponse> findById(@PathVariable Long id) {
        Optional<AgentRHResponse> agent = agentRHService.findById(id);
        return agent.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentRHResponse> updateAgentRH(@PathVariable Long id, @RequestBody AgentRHRequest request) {
        AgentRHResponse agent = agentRHService.updateAgentRH(id, request);
        return ResponseEntity.ok(agent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgentRH(@PathVariable Long id) {
        agentRHService.deleteAgentRH(id);
        return ResponseEntity.noContent().build();
    }
}