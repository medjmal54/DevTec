package com.devtec.gestion_stages.service;

import com.devtec.gestion_stages.entity.Compte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CompteService compteService;

    public CustomUserDetailsService(CompteService compteService) {
        this.compteService = compteService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Loading user by login: {}", login);
        Optional<Compte> compteOpt = compteService.findEntityByLogin(login);
        
        if (!compteOpt.isPresent()) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }

        Compte compte = compteOpt.get();
        
        return User.builder()
            .username(compte.getLogin())
            .password(compte.getMdp())
            .roles("USER")
            .build();
    }
}