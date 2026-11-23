
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "niveaux_etudes")
@Getter
@Setter
@NoArgsConstructor
public class NiveauEtudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String niveau;

    @ManyToMany(mappedBy = "niveauxEtudes")
    private List<ProjetStage> projets = new ArrayList<>();

    @OneToMany(mappedBy = "niveauEtudes")
    private List<Candidature> candidatures = new ArrayList<>();
}
