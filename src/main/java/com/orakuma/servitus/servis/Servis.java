package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="servis")
@EqualsAndHashCode(exclude = "units")
public class Servis implements Serializable {
    private static final long serialVersionUID = 946132719596728289L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servis_gen")
    @SequenceGenerator(name = "servis_gen", sequenceName = "servis_seq", allocationSize = 1)
    private Long id;
    @NotNull(message = "Item name is required")
    @Basic(optional = false)
    private String name;
    private Double price;
    @Enumerated(EnumType.STRING)
    private ServisType servisType;
    private String additionalDetails;
    private LocalDate created;
    private LocalDate changeDate;
    private Boolean active;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "servis_unit",
            joinColumns = @JoinColumn(name = "servis_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private Set<Unit> units = new HashSet<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ServisType getServisType() {
        return servisType;
    }

    public void setServisType(ServisType servisType) {
        this.servisType = servisType;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}
