package com.orakuma.servitus.contact;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true, chain = true)
@Entity
@Table(name = "contacts_entities", schema = "servitus")
public class EntityContact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_entity_gen")
    @SequenceGenerator(name = "contact_entity_gen", sequenceName = "contact_entity_seq", allocationSize = 1)
    @ToString.Exclude
    private Long id;
    @Column(name = "entity_type", nullable = false)
    private String entityType;
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;
}
