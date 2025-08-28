package com.orakuma.servitus.contact;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true, fluent = true)
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_gen")
    @SequenceGenerator(name = "contact_gen", sequenceName = "contact_seq", allocationSize = 1)
    @ToString.Exclude
    private Long        id;
    private String      name;
    private String      phone;
    private String      email;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private LocalDateTime created;
}
