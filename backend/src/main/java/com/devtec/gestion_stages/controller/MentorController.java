package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.MentorRequest;
import com.devtec.gestion_stages.dto.MentorResponse;
import com.devtec.gestion_stages.service.MentorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping
    public ResponseEntity<MentorResponse> createMentor(@RequestBody MentorRequest request) {
        MentorResponse mentor = mentorService.createMentor(request);
        return new ResponseEntity<>(mentor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MentorResponse>> findAll() {
        List<MentorResponse> mentors = mentorService.findAll();
        return ResponseEntity.ok(mentors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorResponse> findById(@PathVariable Long id) {
        Optional<MentorResponse> mentor = mentorService.findById(id);
        return mentor.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorResponse> updateMentor(@PathVariable Long id, @RequestBody MentorRequest request) {
        MentorResponse mentor = mentorService.updateMentor(id, request);
        return ResponseEntity.ok(mentor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        mentorService.deleteMentor(id);
        return ResponseEntity.noContent().build();
    }
}