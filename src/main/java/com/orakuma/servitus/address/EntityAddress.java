package com.orakuma.servitus.address;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Accessors(fluent = true, chain = true)
@Table(name = "addresses_entities", schema = "servitus")
public class EntityAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_entities_gen")
    @SequenceGenerator(name = "addresses_entities_gen", sequenceName = "addresses_entities_seq", allocationSize = 1)
    @ToString.Exclude
    private Long id;
    @Column(name = "entity_type", nullable = false)
    private String entityType;
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
