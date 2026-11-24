package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.dto.MentorRequest;
import com.devtec.gestion_stages.dto.MentorResponse;
import com.devtec.gestion_stages.entity.Mentor;
import com.devtec.gestion_stages.exception.ResourceNotFoundException;
import com.devtec.gestion_stages.repository.MentorRepository;
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
public class MentorService {

    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    public MentorService(MentorRepository mentorRepository, ModelMapper modelMapper) {
        this.mentorRepository = mentorRepository;
        this.modelMapper = modelMapper;
    }

    public MentorResponse createMentor(MentorRequest request) {
        log.info("Creating mentor: {}", request.nom());
        Mentor mentor = new Mentor();
        mentor.setNom(request.nom());
        mentor.setSpecialite(request.specialite());
        Mentor saved = mentorRepository.save(mentor);
        log.info("Mentor created with id: {}", saved.getId());
        return modelMapper.map(saved, MentorResponse.class);
    }

    @Transactional(readOnly = true)
    public List<MentorResponse> findAll() {
        log.debug("Finding all mentors");
        return mentorRepository.findAll().stream()
            .map(mentor -> modelMapper.map(mentor, MentorResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<MentorResponse> findById(Long id) {
        log.debug("Finding mentor by id: {}", id);
        return mentorRepository.findById(id).map(mentor -> modelMapper.map(mentor, MentorResponse.class));
    }

    public MentorResponse updateMentor(Long id, MentorRequest request) {
        log.info("Updating mentor with id: {}", id);
        Mentor mentor = mentorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found with id: " + id));
        mentor.setNom(request.nom());
        mentor.setSpecialite(request.specialite());
        Mentor saved = mentorRepository.save(mentor);
        log.info("Mentor updated with id: {}", saved.getId());
        return modelMapper.map(saved, MentorResponse.class);
    }

    public void deleteMentor(Long id) {
        log.info("Deleting mentor with id: {}", id);
        Mentor mentor = mentorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found with id: " + id));
        mentorRepository.delete(mentor);
    }
}