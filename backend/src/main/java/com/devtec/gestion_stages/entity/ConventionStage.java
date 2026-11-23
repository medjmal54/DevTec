
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "conventions_stage")
@Getter
@Setter
@NoArgsConstructor
public class ConventionStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private LocalDate dateCreation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidature_id",
                nullable = false,
                unique = true)
    private Candidature candidature;
}
