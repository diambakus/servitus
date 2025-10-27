package com.orakuma.servitus.dependency;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "dependencies", schema = "servitus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DependencyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dependency_gen")
    @SequenceGenerator(name = "dependency_gen", sequenceName = "dependency_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer position;
    private boolean active;
    private LocalDateTime created;
}
