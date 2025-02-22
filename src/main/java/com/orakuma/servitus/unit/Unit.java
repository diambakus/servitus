package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.servis.Servis;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(exclude = {"attributes", "servisSet"})
@Entity
@Table(name = "units")
public class Unit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_gen")
    @SequenceGenerator(name ="unit_gen", sequenceName = "unit_seq", allocationSize = 1)
    @ToString.Exclude
    private Long      id;
    @Column(unique = true, nullable = false)
    private String    name;
    @Lob
    private String    note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_organ")
    private Organ     organ;
    @ElementCollection
    @CollectionTable(name = "unit_attributes", joinColumns = @JoinColumn(name = "unit_id"))
    @MapKeyColumn(name = "property")
    @Column(name = "property_value")
    private Map<String, String> attributes = new LinkedHashMap<>();
    private LocalDate created;
    private LocalDate modified;
    private Boolean   active;
    @ManyToMany(mappedBy = "units")
    private Set<Servis> servisSet = new HashSet<>();

    public Unit(){
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
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

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public Set<Servis> getServisSet() {
        return Collections.unmodifiableSet(servisSet);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Unit setAttributes(Map<String, String> attributes) {
        if (attributes == null) {
            this.attributes = new LinkedHashMap<>();
        } else {
            this.attributes = new LinkedHashMap<>(attributes);
        }
        return this;
    }

    public void setServisSet(Set<Servis> servisSet) {
        if (servisSet == null) {
            this.servisSet = new HashSet<>();
        } else {
            this.servisSet = new HashSet<>(servisSet);
        }
    }
}