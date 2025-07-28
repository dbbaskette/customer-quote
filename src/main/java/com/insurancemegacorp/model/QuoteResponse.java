package com.insurancemegacorp.model;

import java.util.Date;
import java.util.Map;

public class QuoteResponse {

    private String quoteId;
    private Customer customer;
    private Vehicle vehicle;
    private Map<String, Double> coverages;
    private Date expirationDate;

    public QuoteResponse(String quoteId, Customer customer, Vehicle vehicle, Map<String, Double> coverages, Date expirationDate) {
        this.quoteId = quoteId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.coverages = coverages;
        this.expirationDate = expirationDate;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Map<String, Double> getCoverages() {
        return coverages;
    }

    public void setCoverages(Map<String, Double> coverages) {
        this.coverages = coverages;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
