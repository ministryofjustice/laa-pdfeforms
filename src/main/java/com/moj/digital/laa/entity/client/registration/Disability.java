package com.moj.digital.laa.entity.client.registration;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "disability")
public class Disability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disability_generator")
    @SequenceGenerator(name = "disability_generator", sequenceName = "Disability_Seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person client;

    @Column(name="disability_option")
    private String disabilityOption;

    @Override
    public String toString() {
        return "Disability{" +
                "id=" + id +
                ", disabilityOption='" + disabilityOption + '\'' +
                '}';
    }
}
