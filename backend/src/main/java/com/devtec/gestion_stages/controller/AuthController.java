package com.devtec.gestion_stages.controller;

import com.devtec.gestion_stages.dto.CompteRequest;
import com.devtec.gestion_stages.dto.CompteResponse;
import com.devtec.gestion_stages.security.JwtTokenProvider;
import com.devtec.gestion_stages.service.CompteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CompteService compteService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          CompteService compteService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.compteService = compteService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("Login request for user: {}", request.login());
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.login(), request.mdp()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = tokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<CompteResponse> register(@RequestBody CompteRequest request) {
        log.info("Registration request for user: {}", request.login());
        CompteResponse compte = compteService.createCompte(request);
        return new ResponseEntity<>(compte, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<CompteResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String username = authentication.getName();
        Optional<CompteResponse> compte = compteService.findByLogin(username);
        
        return compte.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public record AuthRequest(String login, String mdp) {}
    public record AuthResponse(String token) {}
}