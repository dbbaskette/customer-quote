package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Vehicle entities.
 * Extends BaseRepository for common CRUD operations and soft delete functionality.
 */
@Repository
public interface VehicleRepository extends BaseRepository<Vehicle, Long> {

    /**
     * Find vehicles by VIN (Vehicle Identification Number).
     *
     * @param vin the VIN to search for
     * @return an Optional containing the vehicle if found
     */
    Optional<Vehicle> findByVin(String vin);
    
    /**
     * Find vehicles by make.
     *
     * @param make the make to search for (e.g., "Toyota", "Ford")
     * @return list of vehicles with the specified make
     */
    List<Vehicle> findByMake(String make);
    
    /**
     * Find vehicles by make and model.
     *
     * @param make the make to search for
     * @param model the model to search for
     * @return list of vehicles matching both make and model
     */
    List<Vehicle> findByMakeAndModel(String make, String model);
    
    /**
     * Find vehicles by model year.
     *
     * @param year the model year to search for
     * @return list of vehicles from the specified year
     */
    List<Vehicle> findByYear(int year);
    
    /**
     * Find vehicles by model year range.
     *
     * @param startYear the start year (inclusive)
     * @param endYear the end year (inclusive)
     * @return list of vehicles within the specified year range
     */
    List<Vehicle> findByYearBetween(int startYear, int endYear);
    
    /**
     * Find vehicles by body style.
     *
     * @param bodyStyle the body style to search for (e.g., "SEDAN", "SUV")
     * @return list of vehicles with the specified body style
     */
    List<Vehicle> findByBodyStyle(String bodyStyle);
    
    /**
     * Find vehicles by vehicle type.
     *
     * @param vehicleType the vehicle type to search for (e.g., "CAR", "TRUCK")
     * @return list of vehicles of the specified type
     */
    List<Vehicle> findByVehicleType(String vehicleType);
    
    /**
     * Find vehicles by fuel type.
     *
     * @param fuelType the fuel type to search for (e.g., "GASOLINE", "ELECTRIC")
     * @return list of vehicles with the specified fuel type
     */
    List<Vehicle> findByFuelType(String fuelType);
    
    /**
     * Find vehicles by current value range.
     *
     * @param minValue the minimum value (inclusive)
     * @param maxValue the maximum value (inclusive)
     * @return list of vehicles with current value in the specified range
     */
    List<Vehicle> findByCurrentValueBetween(double minValue, double maxValue);
    
    /**
     * Find vehicles by annual mileage range.
     *
     * @param minMileage the minimum annual mileage (inclusive)
     * @param maxMileage the maximum annual mileage (inclusive)
     * @return list of vehicles with annual mileage in the specified range
     */
    List<Vehicle> findByAnnualMileageBetween(int minMileage, int maxMileage);
    
    /**
     * Find vehicles by primary use.
     *
     * @param primaryUse the primary use to search for (e.g., "PERSONAL", "COMMERCIAL")
     * @return list of vehicles with the specified primary use
     */
    List<Vehicle> findByPrimaryUse(String primaryUse);
    
    /**
     * Find vehicles by ownership type.
     *
     * @param ownershipType the ownership type to search for (e.g., "OWNED", "LEASED")
     * @return list of vehicles with the specified ownership type
     */
    List<Vehicle> findByOwnershipType(String ownershipType);
    
    /**
     * Find vehicles with anti-theft devices.
     *
     * @param hasAntiTheft whether to find vehicles with anti-theft devices
     * @return list of vehicles matching the anti-theft criteria
     */
    List<Vehicle> findByHasAntiTheftDevice(boolean hasAntiTheft);
    
    /**
     * Find vehicles by safety features.
     *
     * @param safetyFeatures the safety features to search for
     * @return list of vehicles with the specified safety features
     */
    @Query("SELECT v FROM Vehicle v WHERE :safetyFeatures MEMBER OF v.safetyFeatures")
    List<Vehicle> findBySafetyFeature(@Param("safetyFeatures") String safetyFeatures);
    
    /**
     * Find vehicles by garaging state.
     * Uses the embedded garaging address relationship.
     *
     * @param state the state to search for (2-letter code)
     * @return list of vehicles garaged in the specified state
     */
    @Query("SELECT v FROM Vehicle v WHERE UPPER(v.garagingAddress.state) = UPPER(:state)")
    List<Vehicle> findByGaragingState(@Param("state") String state);
    
    /**
     * Find vehicles by garaging zip code.
     * Uses the embedded garaging address relationship.
     *
     * @param postalCode the postal code to search for
     * @return list of vehicles with the specified garaging postal code
     */
    @Query("SELECT v FROM Vehicle v WHERE v.garagingAddress.postalCode = :postalCode")
    List<Vehicle> findByGaragingPostalCode(@Param("postalCode") String postalCode);
    
    /**
     * Find vehicles by owner (customer) ID.
     *
     * @param ownerId the ID of the owner (customer)
     * @return list of vehicles owned by the specified customer
     */
    List<Vehicle> findByOwnerId(Long ownerId);
    
    /**
     * Find vehicles by license plate number and state.
     *
     * @param licensePlate the license plate number
     * @param state the state that issued the license plate
     * @return an Optional containing the vehicle if found
     */
    Optional<Vehicle> findByLicensePlateAndLicenseState(String licensePlate, String state);
    
    /**
     * Find high-performance vehicles.
     *
     * @param isHighPerformance whether to find high-performance vehicles
     * @return list of vehicles matching the high-performance criteria
     */
    List<Vehicle> findByIsHighPerformance(boolean isHighPerformance);
    
    /**
     * Find antique vehicles (vehicles over 25 years old).
     *
     * @param isAntique whether to find antique vehicles
     * @return list of antique vehicles
     */
    List<Vehicle> findByIsAntique(boolean isAntique);
    
    /**
     * Find vehicles that are used for ridesharing.
     *
     * @param isRideshare whether to find rideshare vehicles
     * @return list of rideshare vehicles
     */
    List<Vehicle> findByIsRideshare(boolean isRideshare);
    
    /**
     * Count vehicles by make.
     *
     * @return list of Object arrays containing make and count
     */
    @Query("SELECT v.make, COUNT(v) FROM Vehicle v GROUP BY v.make ORDER BY COUNT(v) DESC")
    List<Object[]> countVehiclesByMake();
    
    /**
     * Find vehicles that are due for renewal within a date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of vehicles due for renewal in the specified date range
     */
    @Query("SELECT v FROM Vehicle v JOIN v.policies p WHERE p.expirationDate BETWEEN :startDate AND :endDate")
    List<Vehicle> findVehiclesDueForRenewal(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
}