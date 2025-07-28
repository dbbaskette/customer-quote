package com.insurancemegacorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents a physical address in the insurance system.
 * This is an embeddable component that can be used within other entities.
 */
@Embeddable
public class Address {
    
    @Column(name = "street1", nullable = false)
    private String street1;
    
    @Column(name = "street2")
    private String street2;
    
    @Column(nullable = false, length = 100)
    private String city;
    
    @Column(nullable = false, length = 2)
    private String state;
    
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;
    
    @Column(name = "country", length = 2)
    private String country = "US";
    
    @Column(name = "is_primary")
    private Boolean isPrimary = true;
    
    @Column(name = "address_type", length = 20)
    private String addressType; // HOME, WORK, MAILING, GARAGING
    
    @Column(name = "years_at_address")
    private Integer yearsAtAddress;
    
    @Column(name = "months_at_address")
    private Integer monthsAtAddress;
    
    // Default constructor for JPA
    public Address() {
    }
    
    // Convenience constructor
    public Address(String street1, String street2, String city, String state, String postalCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Address(String street1, String street2, String city, String state, String postalCode, String country) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    // Getters and Setters
    public String getStreet1() {
        return street1;
    }
    
    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    
    public String getStreet2() {
        return street2;
    }
    
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public Boolean getPrimary() {
        return isPrimary;
    }
    
    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
    
    public String getAddressType() {
        return addressType;
    }
    
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
    public Integer getYearsAtAddress() {
        return yearsAtAddress;
    }
    
    public void setYearsAtAddress(Integer yearsAtAddress) {
        this.yearsAtAddress = yearsAtAddress;
    }
    
    public Integer getMonthsAtAddress() {
        return monthsAtAddress;
    }
    
    public void setMonthsAtAddress(Integer monthsAtAddress) {
        this.monthsAtAddress = monthsAtAddress;
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "street1='" + street1 + '\'' +
                ", street2='" + street2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}