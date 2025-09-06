package com.orakuma.servitus.address;

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
@Accessors(chain = true)
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", allocationSize = 1)
    @ToString.Exclude
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String addressNumber;
    private Double latitude;
    private Double longitude;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private LocalDateTime created;
}
