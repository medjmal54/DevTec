
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "competences_projet")
@Getter
@Setter
@NoArgsConstructor
public class CompetenceProjet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double poids;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_stage_id", nullable = false)
    private ProjetStage projetStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;
}
