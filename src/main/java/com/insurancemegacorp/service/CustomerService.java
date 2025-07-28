package com.insurancemegacorp.service;

import com.insurancemegacorp.model.Address;
import com.insurancemegacorp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Customer entities.
 * Extends BaseService with additional customer-specific operations.
 */
public interface CustomerService extends BaseService<Customer, Long> {

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
     * Find customers by city.
     *
     * @param city the city to search for
     * @param pageable pagination information
     * @return a page of customers in the specified city
     */
    Page<Customer> findByCity(String city, Pageable pageable);
    
    /**
     * Find customers by state.
     *
     * @param state the state to search for (2-letter code)
     * @param pageable pagination information
     * @return a page of customers in the specified state
     */
    Page<Customer> findByState(String state, Pageable pageable);
    
    /**
     * Find customers by postal code.
     *
     * @param postalCode the postal code to search for
     * @param pageable pagination information
     * @return a page of customers with the specified postal code
     */
    Page<Customer> findByPostalCode(String postalCode, Pageable pageable);
    
    /**
     * Check if a customer with the given email already exists.
     * Excludes soft-deleted customers from the check.
     *
     * @param email the email to check
     * @return true if a customer with the email exists and is active
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if a customer with the given driver's license already exists.
     * Excludes soft-deleted customers from the check.
     *
     * @param driverLicenseNumber the driver's license number to check
     * @param licenseState the state that issued the license
     * @return true if a customer with the license exists and is active
     */
    boolean existsByDriverLicense(String driverLicenseNumber, String licenseState);
    
    /**
     * Update a customer's email address.
     *
     * @param customerId the ID of the customer to update
     * @param newEmail the new email address
     * @return the updated customer
     * @throws RuntimeException if the customer is not found or the email is already in use
     */
    Customer updateEmail(Long customerId, String newEmail);
    
    /**
     * Update a customer's address.
     *
     * @param customerId the ID of the customer to update
     * @param address the new address information
     * @return the updated customer
     * @throws RuntimeException if the customer is not found
     */
    Customer updateAddress(Long customerId, Address address);
    
    /**
     * Update a customer's driver's license information.
     *
     * @param customerId the ID of the customer to update
     * @param driverLicenseNumber the new driver's license number
     * @param licenseState the new license state
     * @param licenseExpiryDate the new license expiry date
     * @return the updated customer
     * @throws RuntimeException if the customer is not found
     */
    Customer updateDriverLicenseInfo(
        Long customerId, 
        String driverLicenseNumber, 
        String licenseState, 
        LocalDate licenseExpiryDate
    );
    
    /**
     * Update a customer's credit rating.
     *
     * @param customerId the ID of the customer to update
     * @param creditRating the new credit rating
     * @param creditScore the new credit score
     * @param creditBureau the credit bureau that provided the rating
     * @param creditReportDate the date of the credit report
     * @return the updated customer
     * @throws RuntimeException if the customer is not found
     */
    Customer updateCreditRating(
        Long customerId,
        String creditRating,
        Integer creditScore,
        String creditBureau,
        LocalDate creditReportDate
    );
    
    /**
     * Add a claim to a customer's record.
     *
     * @param customerId the ID of the customer
     * @param claimId the ID of the claim to add
     * @return the updated customer
     * @throws RuntimeException if the customer is not found
     */
    Customer addClaim(Long customerId, String claimId);
    
    /**
     * Remove a claim from a customer's record.
     *
     * @param customerId the ID of the customer
     * @param claimId the ID of the claim to remove
     * @return the updated customer
     * @throws RuntimeException if the customer is not found
     */
    Customer removeClaim(Long customerId, String claimId);
    
    /**
     * Add a vehicle to a customer's record.
     *
     * @param customerId the ID of the customer
     * @param vehicleId the ID of the vehicle to add
     * @return the updated customer
     * @throws RuntimeException if the customer or vehicle is not found
     */
    Customer addVehicle(Long customerId, Long vehicleId);
    
    /**
     * Remove a vehicle from a customer's record.
     *
     * @param customerId the ID of the customer
     * @param vehicleId the ID of the vehicle to remove
     * @return the updated customer
     * @throws RuntimeException if the customer or vehicle is not found
     */
    Customer removeVehicle(Long customerId, Long vehicleId);
    
    /**
     * Add a policy to a customer's record.
     *
     * @param customerId the ID of the customer
     * @param policyId the ID of the policy to add
     * @return the updated customer
     * @throws RuntimeException if the customer or policy is not found
     */
    Customer addPolicy(Long customerId, Long policyId);
    
    /**
     * Remove a policy from a customer's record.
     *
     * @param customerId the ID of the customer
     * @param policyId the ID of the policy to remove
     * @return the updated customer
     * @throws RuntimeException if the customer or policy is not found
     */
    Customer removePolicy(Long customerId, Long policyId);
    
    /**
     * Find customers with active policies.
     *
     * @param pageable pagination information
     * @return a page of customers with active policies
     */
    Page<Customer> findCustomersWithActivePolicies(Pageable pageable);
    
    /**
     * Find customers with expired policies.
     *
     * @param pageable pagination information
     * @return a page of customers with expired policies
     */
    Page<Customer> findCustomersWithExpiredPolicies(Pageable pageable);
    
    /**
     * Find customers with claims in the last N years.
     *
     * @param years the number of years to look back
     * @param pageable pagination information
     * @return a page of customers with recent claims
     */
    Page<Customer> findCustomersWithRecentClaims(int years, Pageable pageable);
    
    /**
     * Checks if a customer qualifies as a good driver.
     * A good driver is defined as someone with no DUIs, no at-fault accidents,
     * and no more than one minor traffic violation in the past 3 years.
     *
     * @param customerId the ID of the customer to check
     * @return true if the customer qualifies as a good driver, false otherwise
     */
    boolean isGoodDriver(Long customerId);

    /**
     * Checks if a customer has other policies.
     *
     * @param customerId the ID of the customer to check
     * @return true if the customer has other policies, false otherwise
     */
    boolean hasOtherPolicies(Long customerId);
}