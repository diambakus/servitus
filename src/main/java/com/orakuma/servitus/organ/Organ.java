package com.orakuma.servitus.organ;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "organs", schema = "servitus")
public class Organ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_seq")
    @SequenceGenerator(name ="org_seq", sequenceName = "org_seq", allocationSize = 1)
    @ToString.Exclude
    private Long      id;
    @Column(unique = true, nullable = false)
    private String    name;
    @Column(columnDefinition = "text")
    private String    description;
    private LocalDate created;
    private LocalDate modified;
    private Boolean   active;

    public Organ() {
    }

    public Organ(Organ organ) {
        this.id = organ.getId();
        this.name = organ.getName();
        this.description = organ.getDescription();
        this.created = organ.getCreated();
        this.modified = organ.getModified();
        this.active = organ.getActive();
    }
}