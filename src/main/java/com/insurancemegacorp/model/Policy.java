package com.insurancemegacorp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an insurance policy in the system.
 * Contains policy details, coverages, and related information.
 */
@Entity
@Table(name = "policies")
public class Policy implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer policyHolder;
    
    @Column(name = "policy_type", nullable = false, length = 20)
    private String policyType; // AUTO, HOME, RENTERS, etc.
    
    @Column(name = "policy_status", nullable = false, length = 20)
    private String policyStatus; // QUOTE, BOUND, ACTIVE, CANCELLED, EXPIRED, etc.
    
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    
    @Column(name = "issue_date")
    private LocalDate issueDate;
    
    @Column(name = "bind_date")
    private LocalDate bindDate;
    
    @Column(name = "cancellation_date")
    private LocalDate cancellationDate;
    
    @Column(name = "cancellation_reason", length = 255)
    private String cancellationReason;
    
    @Column(name = "term_length_months")
    private Integer termLengthMonths = 12;
    
    @Column(name = "annual_premium", precision = 10, scale = 2)
    private BigDecimal annualPremium;
    
    @Column(name = "total_premium", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPremium = BigDecimal.ZERO;
    
    @Column(name = "total_discounts", precision = 10, scale = 2)
    private BigDecimal totalDiscounts = BigDecimal.ZERO;
    
    @Column(name = "total_surcharges", precision = 10, scale = 2)
    private BigDecimal totalSurcharges = BigDecimal.ZERO;
    
    @Column(name = "total_taxes_fees", precision = 10, scale = 2)
    private BigDecimal totalTaxesFees = BigDecimal.ZERO;
    
    @Column(name = "payment_plan", length = 20)
    private String paymentPlan; // FULL, MONTHLY, QUARTERLY, SEMI_ANNUAL
    
    @Column(name = "down_payment_required")
    private Boolean downPaymentRequired = false;
    
    @Column(name = "down_payment_amount", precision = 10, scale = 2)
    private BigDecimal downPaymentAmount;
    
    @Column(name = "installment_fee", precision = 6, scale = 2)
    private BigDecimal installmentFee = BigDecimal.ZERO;
    
    @Column(name = "is_paperless")
    private Boolean isPaperless = false;
    
    @Column(name = "is_auto_pay")
    private Boolean isAutoPay = false;
    
    @Column(name = "is_paid_in_full")
    private Boolean isPaidInFull = false;
    
    @Column(name = "is_renewal")
    private Boolean isRenewal = false;
    
    @Column(name = "prior_policy_number", length = 50)
    private String priorPolicyNumber;
    
    @Column(name = "prior_insurance_carrier", length = 100)
    private String priorInsuranceCarrier;
    
    @Column(name = "prior_insurance_expiration")
    private LocalDate priorInsuranceExpiration;
    
    @Column(name = "prior_insurance_years")
    private Integer priorInsuranceYears;
    
    @Column(name = "is_prior_insurance_verified")
    private Boolean isPriorInsuranceVerified = false;
    
    @Column(name = "agent_code", length = 20)
    private String agentCode;
    
    @Column(name = "agency_code", length = 20)
    private String agencyCode;
    
    @Column(name = "underwriting_notes", columnDefinition = "TEXT")
    private String underwritingNotes;
    
    @Column(name = "special_conditions", columnDefinition = "TEXT")
    private String specialConditions;
    
    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coverage> coverages = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "policy_vehicles",
        joinColumns = @JoinColumn(name = "policy_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private Set<Vehicle> coveredVehicles = new HashSet<>();
    
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    // Default constructor for JPA
    public Policy() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPolicyNumber() {
        return policyNumber;
    }
    
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    
    public Customer getPolicyHolder() {
        return policyHolder;
    }
    
    public void setPolicyHolder(Customer policyHolder) {
        this.policyHolder = policyHolder;
    }
    
    public String getPolicyType() {
        return policyType;
    }
    
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }
    
    public String getPolicyStatus() {
        return policyStatus;
    }
    
    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }
    
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getBindDate() {
        return bindDate;
    }
    
    public void setBindDate(LocalDate bindDate) {
        this.bindDate = bindDate;
    }
    
    public LocalDate getCancellationDate() {
        return cancellationDate;
    }
    
    public void setCancellationDate(LocalDate cancellationDate) {
        this.cancellationDate = cancellationDate;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public Integer getTermLengthMonths() {
        return termLengthMonths;
    }
    
    public void setTermLengthMonths(Integer termLengthMonths) {
        this.termLengthMonths = termLengthMonths;
    }
    
    public BigDecimal getAnnualPremium() {
        return annualPremium;
    }
    
    public void setAnnualPremium(BigDecimal annualPremium) {
        this.annualPremium = annualPremium;
    }
    
    public BigDecimal getTotalPremium() {
        return totalPremium;
    }
    
    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }
    
    public BigDecimal getTotalDiscounts() {
        return totalDiscounts;
    }
    
    public void setTotalDiscounts(BigDecimal totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }
    
    public BigDecimal getTotalSurcharges() {
        return totalSurcharges;
    }
    
    public void setTotalSurcharges(BigDecimal totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }
    
    public BigDecimal getTotalTaxesFees() {
        return totalTaxesFees;
    }
    
    public void setTotalTaxesFees(BigDecimal totalTaxesFees) {
        this.totalTaxesFees = totalTaxesFees;
    }
    
    public String getPaymentPlan() {
        return paymentPlan;
    }
    
    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }
    
    public Boolean getDownPaymentRequired() {
        return downPaymentRequired;
    }
    
    public void setDownPaymentRequired(Boolean downPaymentRequired) {
        this.downPaymentRequired = downPaymentRequired;
    }
    
    public BigDecimal getDownPaymentAmount() {
        return downPaymentAmount;
    }
    
    public void setDownPaymentAmount(BigDecimal downPaymentAmount) {
        this.downPaymentAmount = downPaymentAmount;
    }
    
    public BigDecimal getInstallmentFee() {
        return installmentFee;
    }
    
    public void setInstallmentFee(BigDecimal installmentFee) {
        this.installmentFee = installmentFee;
    }
    
    public Boolean getPaperless() {
        return isPaperless;
    }
    
    public void setPaperless(Boolean paperless) {
        isPaperless = paperless;
    }
    
    public Boolean getAutoPay() {
        return isAutoPay;
    }
    
    public void setAutoPay(Boolean autoPay) {
        isAutoPay = autoPay;
    }
    
    public Boolean getPaidInFull() {
        return isPaidInFull;
    }
    
    public void setPaidInFull(Boolean paidInFull) {
        isPaidInFull = paidInFull;
    }
    
    public Boolean getRenewal() {
        return isRenewal;
    }
    
    public void setRenewal(Boolean renewal) {
        isRenewal = renewal;
    }
    
    public String getPriorPolicyNumber() {
        return priorPolicyNumber;
    }
    
    public void setPriorPolicyNumber(String priorPolicyNumber) {
        this.priorPolicyNumber = priorPolicyNumber;
    }
    
    public String getPriorInsuranceCarrier() {
        return priorInsuranceCarrier;
    }
    
    public void setPriorInsuranceCarrier(String priorInsuranceCarrier) {
        this.priorInsuranceCarrier = priorInsuranceCarrier;
    }
    
    public LocalDate getPriorInsuranceExpiration() {
        return priorInsuranceExpiration;
    }
    
    public void setPriorInsuranceExpiration(LocalDate priorInsuranceExpiration) {
        this.priorInsuranceExpiration = priorInsuranceExpiration;
    }
    
    public Integer getPriorInsuranceYears() {
        return priorInsuranceYears;
    }
    
    public void setPriorInsuranceYears(Integer priorInsuranceYears) {
        this.priorInsuranceYears = priorInsuranceYears;
    }
    
    public Boolean getPriorInsuranceVerified() {
        return isPriorInsuranceVerified;
    }
    
    public void setPriorInsuranceVerified(Boolean priorInsuranceVerified) {
        isPriorInsuranceVerified = priorInsuranceVerified;
    }
    
    public String getAgentCode() {
        return agentCode;
    }
    
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    
    public String getAgencyCode() {
        return agencyCode;
    }
    
    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
    
    public String getUnderwritingNotes() {
        return underwritingNotes;
    }
    
    public void setUnderwritingNotes(String underwritingNotes) {
        this.underwritingNotes = underwritingNotes;
    }
    
    public String getSpecialConditions() {
        return specialConditions;
    }
    
    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }
    
    public Set<Coverage> getCoverages() {
        return coverages;
    }
    
    public void setCoverages(Set<Coverage> coverages) {
        this.coverages = coverages;
    }
    
    public void addCoverage(Coverage coverage) {
        coverages.add(coverage);
        coverage.setPolicy(this);
    }
    
    public void removeCoverage(Coverage coverage) {
        coverages.remove(coverage);
        coverage.setPolicy(null);
    }
    
    public Set<Vehicle> getCoveredVehicles() {
        return coveredVehicles;
    }
    
    public void setCoveredVehicles(Set<Vehicle> coveredVehicles) {
        this.coveredVehicles = coveredVehicles;
    }
    
    public void addVehicle(Vehicle vehicle) {
        coveredVehicles.add(vehicle);
    }
    
    public void removeVehicle(Vehicle vehicle) {
        coveredVehicles.remove(vehicle);
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isCancelled() {
        return "CANCELLED".equals(policyStatus) && cancellationDate != null;
    }
    
    public boolean isExpired() {
        return "EXPIRED".equals(policyStatus) || 
               (expirationDate != null && LocalDate.now().isAfter(expirationDate));
    }
    
    public boolean isQuote() {
        return "QUOTE".equals(policyStatus);
    }
    
    public boolean isBound() {
        return "BOUND".equals(policyStatus) || "ACTIVE".equals(policyStatus);
    }
    
    public BigDecimal calculateTotalPremium() {
        BigDecimal total = BigDecimal.ZERO;
        
        if (coverages != null) {
            for (Coverage coverage : coverages) {
                if (coverage.getPremium() != null) {
                    total = total.add(coverage.getPremium());
                }
            }
        }
        
        // Apply discounts and surcharges
        if (totalDiscounts != null) {
            total = total.subtract(totalDiscounts);
        }
        
        if (totalSurcharges != null) {
            total = total.add(totalSurcharges);
        }
        
        // Ensure total is not negative
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
        
        return total;
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        
        // Set default values
        if (this.policyStatus == null) {
            this.policyStatus = "QUOTE";
        }
        
        if (this.effectiveDate == null) {
            this.effectiveDate = LocalDate.now();
        }
        
        if (this.expirationDate == null && this.termLengthMonths != null) {
            this.expirationDate = this.effectiveDate.plusMonths(this.termLengthMonths);
        }
        
        // Calculate total premium if not set
        if (this.totalPremium == null || this.totalPremium.compareTo(BigDecimal.ZERO) == 0) {
            this.totalPremium = calculateTotalPremium();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
    
    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", policyNumber='" + policyNumber + '\'' +
                ", policyType='" + policyType + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expirationDate=" + expirationDate +
                ", totalPremium=" + totalPremium +
                '}';
    }
}