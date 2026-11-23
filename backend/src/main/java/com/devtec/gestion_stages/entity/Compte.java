
package com.devtec.gestion_stages.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comptes")
@Getter
@Setter
@NoArgsConstructor
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String mdp;

    @OneToOne
    @JoinColumn(name = "mentor_id", unique = true)
    private Mentor mentor;

    @OneToOne
    @JoinColumn(name = "agent_rh_id", unique = true)
    private AgentRH agentRH;
}
