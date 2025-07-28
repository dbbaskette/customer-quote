package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Customer entities.
 * Extends BaseRepository for common CRUD operations and soft delete functionality.
 */
@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

    /**
     * Find a customer by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the customer if found
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Find customers by last name.
     *
     * @param lastName the last name to search for
     * @return list of customers with the given last name
     */
    List<Customer> findByLastName(String lastName);
    
    /**
     * Find customers by date of birth range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of customers with date of birth in the specified range
     */
    List<Customer> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find customers by driver's license number and state.
     *
     * @param driverLicenseNumber the driver's license number
     * @param licenseState the state that issued the license
     * @return an Optional containing the customer if found
     */
    Optional<Customer> findByDriverLicenseNumberAndLicenseState(String driverLicenseNumber, String licenseState);
    
    /**
     * Find customers by credit rating range.
     *
     * @param minScore the minimum credit score (inclusive)
     * @param maxScore the maximum credit score (inclusive)
     * @return list of customers with credit scores in the specified range
     */
    List<Customer> findByCreditRatingBetween(int minScore, int maxScore);
    
    /**
     * Find customers with active policies.
     * Uses a custom query to join with the policies table.
     *
     * @param pageable pagination information
     * @return a page of customers with active policies
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.policies p WHERE p.active = true")
    Page<Customer> findCustomersWithActivePolicies(Pageable pageable);

    /**
     * Find customers with expired policies.
     * Uses a custom query to join with the policies table.
     *
     * @param pageable pagination information
     * @return a page of customers with expired policies
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.policies p WHERE p.active = false")
    Page<Customer> findCustomersWithExpiredPolicies(Pageable pageable);

    /**
     * Find customers with claims in the last N months.
     *
     * @param cutoffDate the cutoff date for recent claims
     * @param pageable pagination information
     * @return a page of customers with recent claims
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.claims cl WHERE cl.dateOfClaim >= :cutoffDate")
    Page<Customer> findCustomersWithRecentClaims(@Param("cutoffDate") LocalDate cutoffDate, Pageable pageable);
    
    /**
     * Find customers by city.
     * Uses the embedded address relationship.
     *
     * @param city the city to search for
     * @param pageable pagination information
     * @return a page of customers in the specified city
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.address.city) = LOWER(:city)")
    Page<Customer> findByCity(@Param("city") String city, Pageable pageable);
    
    /**
     * Find customers by state.
     * Uses the embedded address relationship.
     *
     * @param state the state to search for (2-letter code)
     * @param pageable pagination information
     * @return a page of customers in the specified state
     */
    @Query("SELECT c FROM Customer c WHERE UPPER(c.address.state) = UPPER(:state)")
    Page<Customer> findByState(@Param("state") String state, Pageable pageable);
    
    /**
     * Find customers by postal code.
     * Uses the embedded address relationship.
     *
     * @param postalCode the postal code to search for
     * @param pageable pagination information
     * @return a page of customers with the specified postal code
     */
    @Query("SELECT c FROM Customer c WHERE c.address.postalCode = :postalCode")
    Page<Customer> findByPostalCode(@Param("postalCode") String postalCode, Pageable pageable);
    
    /**
     * Check if a customer with the given email already exists.
     * Excludes soft-deleted customers from the check.
     *
     * @param email the email to check
     * @return true if a customer with the email exists and is active
     */
    boolean existsByEmailAndActiveTrue(String email);
    
    /**
     * Check if a customer with the given driver's license already exists.
     * Excludes soft-deleted customers from the check.
     *
     * @param driverLicenseNumber the driver's license number to check
     * @param licenseState the state that issued the license
     * @return true if a customer with the license exists and is active
     */
    boolean existsByDriverLicenseNumberAndLicenseStateAndActiveTrue(String driverLicenseNumber, String licenseState);
    
    // Legacy method - kept for backward compatibility
    /**
     * Find a customer by their full name (first + last name).
     * This is a legacy method and may be removed in future versions.
     *
     * @param name the full name to search for (format: "firstName lastName")
     * @return the customer if found, null otherwise
     */
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) = :name")
    Customer findByName(@Param("name") String name);
}