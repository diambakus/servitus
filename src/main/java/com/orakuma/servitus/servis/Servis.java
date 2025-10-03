package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name ="servis")
@EqualsAndHashCode(exclude = {"units", "requisites"})
@ToString
@Getter
@Setter
public class Servis implements Serializable {
    private static final long serialVersionUID = 946132719596728289L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servis_gen")
    @SequenceGenerator(name = "servis_gen", sequenceName = "servis_seq", allocationSize = 1)
    @ToString.Exclude
    private Long id;
    @NotNull(message = "Item name is required")
    @Basic(optional = false)
    private String name;
    private Double price;
    @Enumerated(EnumType.STRING)
    private ServisType servisType;
    private String description;
    private LocalDate created;
    private LocalDate modified;
    private Boolean active;
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "servis_unit",
            joinColumns = @JoinColumn(name = "servis_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private Set<Unit> units = new HashSet<>();
    @ElementCollection
    @CollectionTable(name = "requisites", joinColumns = @JoinColumn(name = "servis_id"))
    @MapKeyColumn(name = "position")
    @Column(name = "title")
    private Map<Integer, String> requisites = new LinkedHashMap<>();

    public Servis() {
    }

    public void addUnit(Unit unit){
        this.units.add(unit);
        unit.getServisSet().add(this);
    }

    public void removeUnit(Unit unit){
        this.units.remove(unit);
        unit.getServisSet().remove(this);
    }

    public Map<Integer, String> getRequisites() {
        return new LinkedHashMap<>(this.requisites);
    }

    public void setUnits(Set<Unit> units) {
        this.units.clear();
        if (units != null) {
            this.units.forEach(this::addUnit);
        }
    }

    public Set<Unit> getUnits() {
        return Collections.unmodifiableSet(this.units);
    }

    public Servis setRequisites(Map<Integer, String> requisites) {
        if (requisites == null) {
            this.requisites = new LinkedHashMap<>();
        } else {
            this.requisites = new LinkedHashMap<>(requisites);
        }
        return this;
    }
}
