package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // For demo/upgrade only; not used in main logic
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    // Demo method
    Vehicle findByMake(String make);
} 