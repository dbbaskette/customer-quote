package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Policy;
import com.insurancemegacorp.model.PolicyStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Policy entities.
 * Extends BaseRepository for common CRUD operations and soft delete functionality.
 */
@Repository
public interface PolicyRepository extends BaseRepository<Policy, Long> {

    /**
     * Find a policy by its policy number.
     *
     * @param policyNumber the policy number to search for
     * @return an Optional containing the policy if found
     */
    Optional<Policy> findByPolicyNumber(String policyNumber);
    
    /**
     * Find policies by status.
     *
     * @param status the policy status to search for
     * @return list of policies with the specified status
     */
    List<Policy> findByPolicyStatus(PolicyStatus status);
    
    /**
     * Find policies by type.
     *
     * @param policyType the policy type to search for
     * @return list of policies of the specified type
     */
    List<Policy> findByPolicyType(String policyType);
    
    /**
     * Find policies by customer ID.
     *
     * @param customerId the ID of the customer
     * @return list of policies for the specified customer
     */
    List<Policy> findByPolicyHolderId(Long customerId);
    
    /**
     * Find policies by customer ID and status.
     *
     * @param customerId the ID of the customer
     * @param status the policy status to filter by
     * @return list of matching policies
     */
    List<Policy> findByPolicyHolderIdAndPolicyStatus(Long customerId, PolicyStatus status);
    
    /**
     * Find policies that are effective on a specific date.
     *
     * @param date the date to check
     * @return list of policies effective on the specified date
     */
    @Query("SELECT p FROM Policy p WHERE p.effectiveDate <= :date AND p.expirationDate >= :date")
    List<Policy> findEffectivePoliciesOnDate(@Param("date") LocalDate date);
    
    /**
     * Find policies that are expiring within a date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of policies expiring in the specified date range
     */
    List<Policy> findByExpirationDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find policies that are expiring soon (within the next 30 days).
     *
     * @return list of policies expiring soon
     */
    @Query("SELECT p FROM Policy p WHERE p.expirationDate BETWEEN CURRENT_DATE AND CURRENT_DATE + 30")
    List<Policy> findPoliciesExpiringSoon();
    
    /**
     * Find policies by payment plan.
     *
     * @param paymentPlan the payment plan to search for
     * @return list of policies with the specified payment plan
     */
    List<Policy> findByPaymentPlan(String paymentPlan);
    
    /**
     * Find policies by agent code.
     *
     * @param agentCode the agent code to search for
     * @return list of policies associated with the specified agent
     */
    List<Policy> findByAgentCode(String agentCode);
    
    /**
     * Find policies by agency code.
     *
     * @param agencyCode the agency code to search for
     * @return list of policies associated with the specified agency
     */
    List<Policy> findByAgencyCode(String agencyCode);
    
    /**
     * Find policies with premium amounts in a specific range.
     *
     * @param minPremium the minimum premium amount (inclusive)
     * @param maxPremium the maximum premium amount (inclusive)
     * @return list of policies with premiums in the specified range
     */
    List<Policy> findByTotalPremiumBetween(double minPremium, double maxPremium);
    
    /**
     * Find policies that have been cancelled.
     *
     * @param cancelled whether to find cancelled or non-cancelled policies
     * @return list of cancelled/non-cancelled policies
     */
    @Query("SELECT p FROM Policy p WHERE (p.cancellationDate IS NOT NULL) = :cancelled")
    List<Policy> findCancelledPolicies(@Param("cancelled") boolean cancelled);
    
    /**
     * Find policies that are due for renewal within a date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of policies due for renewal in the specified date range
     */
    @Query("SELECT p FROM Policy p WHERE p.expirationDate BETWEEN :startDate AND :endDate AND p.policyStatus = 'ACTIVE'")
    List<Policy> findPoliciesDueForRenewal(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
    
    /**
     * Count policies by status.
     *
     * @return list of Object arrays containing status and count
     */
    @Query("SELECT p.policyStatus, COUNT(p) FROM Policy p GROUP BY p.policyStatus")
    List<Object[]> countPoliciesByStatus();
    
    /**
     * Calculate total written premium by policy type.
     *
     * @return list of Object arrays containing policy type and total premium
     */
    @Query("SELECT p.policyType, SUM(p.totalPremium) FROM Policy p GROUP BY p.policyType")
    List<Object[]> calculateTotalPremiumByPolicyType();
    
    /**
     * Find policies that include a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return list of policies that include the specified vehicle
     */
    @Query("SELECT p FROM Policy p JOIN p.coveredVehicles v WHERE v.id = :vehicleId")
    List<Policy> findPoliciesByVehicleId(@Param("vehicleId") Long vehicleId);
    
    /**
     * Find policies with a specific coverage type.
     *
     * @param coverageType the coverage type to search for
     * @return list of policies that include the specified coverage type
     */
    @Query("SELECT DISTINCT p FROM Policy p JOIN p.coverages c WHERE c.coverageType = :coverageType")
    List<Policy> findPoliciesByCoverageType(@Param("coverageType") String coverageType);
    
    /**
     * Find the most recent policy for a customer.
     *
     * @param customerId the ID of the customer
     * @return an Optional containing the most recent policy if found
     */
    @Query("SELECT p FROM Policy p WHERE p.policyHolder.id = :customerId ORDER BY p.issueDate DESC LIMIT 1")
    Optional<Policy> findMostRecentPolicyByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Find policies that were bound within a date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of policies bound in the specified date range
     */
    List<Policy> findByBindDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find policies that were issued within a date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of policies issued in the specified date range
     */
    List<Policy> findByIssueDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Check if a policy with the given policy number already exists.
     * Excludes soft-deleted policies from the check.
     *
     * @param policyNumber the policy number to check
     * @return true if a policy with the number exists and is active
     */
    boolean existsByPolicyNumberAndActiveTrue(String policyNumber);
}
