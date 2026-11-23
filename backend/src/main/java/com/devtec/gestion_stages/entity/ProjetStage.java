
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projets_stage")
@Getter
@Setter
@NoArgsConstructor
public class ProjetStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String objectifs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    @OneToMany(mappedBy = "projetStage")
    private List<Candidature> candidatures = new ArrayList<>();

    @OneToMany(
        mappedBy = "projetStage",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Tache> taches = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "projet_niveau_etudes",
        joinColumns = @JoinColumn(name = "projet_stage_id"),
        inverseJoinColumns = @JoinColumn(name = "niveau_etudes_id")
    )
    private List<NiveauEtudes> niveauxEtudes = new ArrayList<>();

    @OneToMany(
        mappedBy = "projetStage",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<CompetenceProjet> competencesProjet = new ArrayList<>();
}
