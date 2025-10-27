package com.orakuma.servitus.servis;

import com.orakuma.servitus.dependency.DependencyEntity;
import com.orakuma.servitus.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name ="servis", schema = "servitus")
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "servis_dependency",
            joinColumns = @JoinColumn(name = "servis_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    @OrderBy("position ASC")
    @Setter(AccessLevel.NONE)
    private Set<DependencyEntity> dependencies = new LinkedHashSet<>();

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

    public void setUnits(Set<Unit> units) {
        this.units.clear();
        if (units != null) {
            this.units.forEach(this::addUnit);
        }
    }

    public Set<Unit> getUnits() {
        return Collections.unmodifiableSet(this.units);
    }

    public Set<DependencyEntity> getDependencies() {
        return Collections.unmodifiableSet(this.dependencies);
    }

    public void addDependency(DependencyEntity dependencyEntity) {
        dependencies.add(dependencyEntity);
    }

    public void removeDependency(DependencyEntity dependency) {
        dependencies.remove(dependency);
    }
}
