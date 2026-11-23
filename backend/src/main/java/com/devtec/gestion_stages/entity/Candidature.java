
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.devtec.gestion_stages.entity.enums.StatutCandidature;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidatures")
@Getter
@Setter
@NoArgsConstructor
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomCandidat;

    @Column(nullable = false)
    private String email;

    private LocalDate dateCandidature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCandidature statut = StatutCandidature.EnAttente;

    @Transient
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_stage_id", nullable = false)
    private ProjetStage projetStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_etudes_id", nullable = false)
    private NiveauEtudes niveauEtudes;

    @ManyToMany
    @JoinTable(
        name = "candidature_competence",
        joinColumns = @JoinColumn(name = "candidature_id"),
        inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences = new ArrayList<>();

    @OneToOne(mappedBy = "candidature",
              cascade = CascadeType.ALL)
    private ConventionStage conventionStage;
}
