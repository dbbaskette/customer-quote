package com.insurancemegacorp.repository;

import com.insurancemegacorp.model.Coverage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Coverage entities.
 * Extends BaseRepository for common CRUD operations and soft delete functionality.
 */
@Repository
public interface CoverageRepository extends BaseRepository<Coverage, Long> {

    /**
     * Find coverages by policy ID.
     *
     * @param policyId the ID of the policy
     * @return list of coverages for the specified policy
     */
    List<Coverage> findByPolicyId(Long policyId);
    
    /**
     * Find coverages by type.
     *
     * @param coverageType the coverage type to search for
     * @return list of coverages of the specified type
     */
    List<Coverage> findByCoverageType(String coverageType);
    
    /**
     * Find coverages by subtype.
     *
     * @param coverageSubtype the coverage subtype to search for
     * @return list of coverages with the specified subtype
     */
    List<Coverage> findByCoverageSubtype(String coverageSubtype);
    
    /**
     * Find coverages by type and subtype.
     *
     * @param coverageType the coverage type to search for
     * @param coverageSubtype the coverage subtype to search for
     * @return list of coverages matching both type and subtype
     */
    List<Coverage> findByCoverageTypeAndCoverageSubtype(String coverageType, String coverageSubtype);
    
    /**
     * Find coverages by coverage code.
     *
     * @param coverageCode the coverage code to search for
     * @return list of coverages with the specified code
     */
    List<Coverage> findByCoverageCode(String coverageCode);
    
    /**
     * Find coverages by policy ID and type.
     *
     * @param policyId the ID of the policy
     * @param coverageType the coverage type to filter by
     * @return list of matching coverages
     */
    List<Coverage> findByPolicyIdAndCoverageType(Long policyId, String coverageType);
    
    /**
     * Find coverages with premium amounts above a specified value.
     *
     * @param minPremium the minimum premium amount (inclusive)
     * @return list of coverages with premiums above the specified value
     */
    List<Coverage> findByPremiumGreaterThanEqual(BigDecimal minPremium);
    
    /**
     * Find coverages with premium amounts within a specified range.
     *
     * @param minPremium the minimum premium amount (inclusive)
     * @param maxPremium the maximum premium amount (inclusive)
     * @return list of coverages with premiums in the specified range
     */
    List<Coverage> findByPremiumBetween(BigDecimal minPremium, BigDecimal maxPremium);
    
    /**
     * Find coverages by deductible amount.
     *
     * @param deductibleAmount the deductible amount to search for
     * @return list of coverages with the specified deductible amount
     */
    List<Coverage> findByDeductibleAmount(BigDecimal deductibleAmount);
    
    /**
     * Find coverages by deductible type.
     *
     * @param deductibleType the deductible type to search for
     * @return list of coverages with the specified deductible type
     */
    List<Coverage> findByDeductibleType(String deductibleType);
    
    /**
     * Find required coverages.
     *
     * @param isRequired whether to find required or optional coverages
     * @return list of required/optional coverages
     */
    List<Coverage> findByIsRequired(boolean isRequired);
    
    /**
     * Find state-required coverages.
     *
     * @param isStateRequired whether to find state-required coverages
     * @return list of state-required/optional coverages
     */
    List<Coverage> findByIsStateRequired(boolean isStateRequired);
    
    /**
     * Find primary coverages.
     *
     * @param isPrimary whether to find primary or excess coverages
     * @return list of primary/excess coverages
     */
    List<Coverage> findByIsPrimary(boolean isPrimary);
    
    /**
     * Find umbrella coverages.
     *
     * @param isUmbrella whether to find umbrella coverages
     * @return list of umbrella/non-umbrella coverages
     */
    List<Coverage> findByIsUmbrella(boolean isUmbrella);
    
    /**
     * Find coverages with a specific limit amount.
     *
     * @param limitAmount the limit amount to search for
     * @return list of coverages with the specified limit amount
     */
    @Query("SELECT c FROM Coverage c WHERE c.limit1Amount = :limitAmount OR c.limit2Amount = :limitAmount")
    List<Coverage> findByLimitAmount(@Param("limitAmount") BigDecimal limitAmount);
    
    /**
     * Find coverages with limit amounts above a specified value.
     *
     * @param minLimit the minimum limit amount (inclusive)
     * @return list of coverages with limits above the specified value
     */
    @Query("SELECT c FROM Coverage c WHERE c.limit1Amount >= :minLimit OR c.limit2Amount >= :minLimit")
    List<Coverage> findByMinLimit(@Param("minLimit") BigDecimal minLimit);
    
    /**
     * Find coverages by policy ID and required status.
     *
     * @param policyId the ID of the policy
     * @param isRequired whether to find required or optional coverages
     * @return list of matching coverages
     */
    List<Coverage> findByPolicyIdAndIsRequired(Long policyId, boolean isRequired);
    
    /**
     * Find the most expensive coverage for a policy.
     *
     * @param policyId the ID of the policy
     * @return an Optional containing the most expensive coverage if found
     */
    @Query("SELECT c FROM Coverage c WHERE c.policy.id = :policyId ORDER BY c.premium DESC LIMIT 1")
    Optional<Coverage> findMostExpensiveCoverageByPolicyId(@Param("policyId") Long policyId);
    
    /**
     * Calculate the total premium for all coverages of a policy.
     *
     * @param policyId the ID of the policy
     * @return the sum of all coverage premiums for the policy
     */
    @Query("SELECT COALESCE(SUM(c.premium), 0) FROM Coverage c WHERE c.policy.id = :policyId")
    BigDecimal calculateTotalPremiumByPolicyId(@Param("policyId") Long policyId);
    
    /**
     * Count coverages by type for a policy.
     *
     * @param policyId the ID of the policy
     * @return list of Object arrays containing coverage type and count
     */
    @Query("SELECT c.coverageType, COUNT(c) FROM Coverage c WHERE c.policy.id = :policyId GROUP BY c.coverageType")
    List<Object[]> countCoveragesByTypeForPolicy(@Param("policyId") Long policyId);
    
    /**
     * Find coverages with notes containing the specified text.
     *
     * @param searchText the text to search for in coverage notes
     * @return list of coverages with matching notes
     */
    @Query("SELECT c FROM Coverage c WHERE LOWER(c.coverageNotes) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Coverage> searchByNotes(@Param("searchText") String searchText);
    
    /**
     * Find custom coverages (those with isCustom = true).
     *
     * @param isCustom whether to find custom or standard coverages
     * @return list of custom/standard coverages
     */
    List<Coverage> findByIsCustom(boolean isCustom);
    
    /**
     * Find coverages that include rental reimbursement.
     *
     * @param isRentalReimbursement whether to find coverages with rental reimbursement
     * @return list of coverages with/without rental reimbursement
     */
    List<Coverage> findByIsRentalReimbursement(boolean isRentalReimbursement);
    
    /**
     * Find coverages that include roadside assistance.
     *
     * @param isRoadsideAssistance whether to find coverages with roadside assistance
     * @return list of coverages with/without roadside assistance
     */
    List<Coverage> findByIsRoadsideAssistance(boolean isRoadsideAssistance);
    
    /**
     * Find coverages that include medical payments.
     *
     * @param isMedicalPayments whether to find coverages with medical payments
     * @return list of coverages with/without medical payments
     */
    List<Coverage> findByIsMedicalPayments(boolean isMedicalPayments);
    
    /**
     * Find coverages that include personal injury protection (PIP).
     *
     * @param isPIP whether to find coverages with PIP
     * @return list of coverages with/without PIP
     */
    List<Coverage> findByIsPIP(boolean isPIP);
    
    /**
     * Find coverages that include uninsured/underinsured motorist property damage (UMPD).
     *
     * @param isUMPD whether to find coverages with UMPD
     * @return list of coverages with/without UMPD
     */
    List<Coverage> findByIsUMPD(boolean isUMPD);
    
    /**
     * Find coverages that include uninsured/underinsured motorist bodily injury (UMBI).
     *
     * @param isUMBI whether to find coverages with UMBI
     * @return list of coverages with/without UMBI
     */
    List<Coverage> findByIsUMBI(boolean isUMBI);
}
