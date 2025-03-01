package com.orakuma.servitus.organ;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "organs")
public class Organ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_seq")
    @SequenceGenerator(name ="org_seq", sequenceName = "org_seq", allocationSize = 1)
    @ToString.Exclude
    private Long      id;
    @Column(unique = true, nullable = false)
    private String    name;
    @Lob
    private String    note;
    @EqualsAndHashCode.Exclude
    @ElementCollection
    @CollectionTable(name = "organization_attributes", joinColumns = @JoinColumn(name = "organ_id"))
    @MapKeyColumn(name = "property")
    @Column(name = "property_value")
    private Map<String, String> attributes = new LinkedHashMap<>();
    private LocalDate created;
    private LocalDate modified;
    private Boolean   active;

    public Organ() {
    }

    public Organ(Organ organ) {
        this.id = organ.getId();
        this.name = organ.getName();
        this.note = organ.getNote();
        this.created = organ.getCreated();
        this.modified = organ.getModified();
        this.active = organ.getActive();
        this.attributes = new LinkedHashMap<>(organ.getAttributes());
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        if (attributes == null) {
            this.attributes = new LinkedHashMap<>();
        } else {
            this.attributes = new LinkedHashMap<>(attributes);
        }
    }
}