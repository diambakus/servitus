package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.servis.Servis;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@ToString
@Entity
@Table(name = "units", schema = "servitus")
@Getter
@Setter
public class Unit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_gen")
    @SequenceGenerator(name ="unit_gen", sequenceName = "unit_seq", allocationSize = 1)
    @ToString.Exclude
    private Long      id;
    @Column(unique = true, nullable = false)
    private String    name;
    @Column(columnDefinition = "text")
    private String    description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_organ")
    private Organ     organ;
    private LocalDate created;
    private LocalDate modified;
    private Boolean   active;
    @ManyToMany(mappedBy = "units", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Servis> servisSet = new HashSet<>();
    private String publicId;

    public Organ getOrgan() {
        return organ == null ? null : new Organ(organ);
    }

    public void setOrgan(Organ organ) {
        if (organ == null) {
            this.organ = null;
        } else {
            this.organ = new Organ(organ);
        }
    }

    public Set<Servis> getServisSet() {
        return new HashSet<>(servisSet);
    }

    public void setServisSet(Set<Servis> servisSet) {
        if (servisSet == null) {
            this.servisSet = new HashSet<>();
        } else {
            this.servisSet = new HashSet<>(servisSet);
        }
    }
}