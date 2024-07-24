package com.chervonnaya.objectmappermvcproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name="address_city")
    public String city;
    @Column(name="address_street")
    public String street;
    @Column(name="address_home")
    public String home;
    @Column(name="address_apartment")
    public String apartment;
}
