
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competences")
@Getter
@Setter
@NoArgsConstructor
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "competences")
    private List<Candidature> candidatures = new ArrayList<>();

    @OneToMany(mappedBy = "competence")
    private List<CompetenceProjet> projets = new ArrayList<>();
}
