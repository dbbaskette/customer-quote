package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // For demo/upgrade only; not used in main logic
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // Demo method
    Customer findByName(String name);
}
