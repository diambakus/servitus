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

    public Unit(){
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public Boolean getActive() {
        return active;
    }

    public Organ getOrgan() {
        return organ == null ? null : new Organ(organ);
    }

    public Set<Servis> getServisSet() {
        return new HashSet<>(servisSet);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrgan(Organ organ) {
        if (organ == null) {
            this.organ = null;
        } else {
            this.organ = new Organ(organ);
        }
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public void setServisSet(Set<Servis> servisSet) {
        if (servisSet == null) {
            this.servisSet = new HashSet<>();
        } else {
            this.servisSet = new HashSet<>(servisSet);
        }
    }
}