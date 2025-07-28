package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepositoryImpl {

    private final Map<String, Customer> customers = new HashMap<>();

    public CustomerRepositoryImpl() {
        customers.put("123", new Customer("123", "John Doe", 30));
        customers.put("456", new Customer("456", "Jane Smith", 22));
    }

    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(customers.get(id));
    }
}
