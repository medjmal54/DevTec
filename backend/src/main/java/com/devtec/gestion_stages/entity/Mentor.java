
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@NoArgsConstructor
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String specialite;

    @OneToOne(mappedBy = "mentor")
    private Compte compte;

    @OneToMany(mappedBy = "mentor")
    private List<ProjetStage> projets = new ArrayList<>();
}
