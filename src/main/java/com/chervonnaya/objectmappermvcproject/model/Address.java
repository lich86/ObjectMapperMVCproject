package com.chervonnaya.objectmappermvcproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(name="address_city")
    public String city;
    @Column(name="address_street")
    public String street;
    @Column(name="address_home")
    public int home;
    @Column(name="address_apartment")
    public int apartment;
}
