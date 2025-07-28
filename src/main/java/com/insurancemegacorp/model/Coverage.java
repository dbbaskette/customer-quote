package com.insurancemegacorp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an insurance coverage or endorsement on a policy.
 * Each coverage has its own limits, deductibles, and premium.
 */
@Entity
@Table(name = "coverages")
public class Coverage implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;
    
    @Column(name = "coverage_type", nullable = false, length = 50)
    private String coverageType; // LIABILITY, COLLISION, COMPREHENSIVE, etc.
    
    @Column(name = "coverage_subtype", length = 50)
    private String coverageSubtype; // BODILY_INJURY, PROPERTY_DAMAGE, etc.
    
    @Column(name = "coverage_code", length = 20)
    private String coverageCode; // Standard code for the coverage
    
    @Column(name = "coverage_name", length = 100)
    private String coverageName; // Display name for the coverage
    
    @Column(name = "limit1_amount", precision = 12, scale = 2)
    private BigDecimal limit1Amount; // First limit amount (e.g., per person)
    
    @Column(name = "limit1_description", length = 50)
    private String limit1Description; // Description of first limit
    
    @Column(name = "limit2_amount", precision = 12, scale = 2)
    private BigDecimal limit2Amount; // Second limit amount (e.g., per accident)
    
    @Column(name = "limit2_description", length = 50)
    private String limit2Description; // Description of second limit
    
    @Column(name = "deductible_amount", precision = 10, scale = 2)
    private BigDecimal deductibleAmount;
    
    @Column(name = "deductible_type", length = 20)
    private String deductibleType; // STANDARD, GLASS, etc.
    
    @Column(name = "premium", precision = 10, scale = 2, nullable = false)
    private BigDecimal premium = BigDecimal.ZERO;
    
    @Column(name = "is_required")
    private Boolean isRequired = false;
    
    @Column(name = "is_optional")
    private Boolean isOptional = true;
    
    @Column(name = "is_default_selected")
    private Boolean isDefaultSelected = false;
    
    @Column(name = "is_state_required")
    private Boolean isStateRequired = false;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @Column(name = "is_excess")
    private Boolean isExcess = false;
    
    @Column(name = "is_umbrella")
    private Boolean isUmbrella = false;
    
    @Column(name = "is_rental_reimbursement")
    private Boolean isRentalReimbursement = false;
    
    @Column(name = "is_roadside_assistance")
    private Boolean isRoadsideAssistance = false;
    
    @Column(name = "is_medical_payments")
    private Boolean isMedicalPayments = false;
    
    @Column(name = "is_pip")
    private Boolean isPIP = false; // Personal Injury Protection
    
    @Column(name = "is_umpd")
    private Boolean isUMPD = false; // Uninsured Motorist Property Damage
    
    @Column(name = "is_umbi")
    private Boolean isUMBI = false; // Uninsured/Underinsured Motorist Bodily Injury
    
    @Column(name = "is_custom")
    private Boolean isCustom = false;
    
    @Column(name = "custom_description", columnDefinition = "TEXT")
    private String customDescription;
    
    @Column(name = "coverage_notes", columnDefinition = "TEXT")
    private String coverageNotes;
    
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    // Default constructor for JPA
    public Coverage() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    // Constructor for common coverage types
    public Coverage(String coverageType, String coverageSubtype, BigDecimal limit1, BigDecimal limit2, 
                   BigDecimal deductible, BigDecimal premium) {
        this();
        this.coverageType = coverageType;
        this.coverageSubtype = coverageSubtype;
        this.limit1Amount = limit1;
        this.limit2Amount = limit2;
        this.deductibleAmount = deductible;
        this.premium = premium != null ? premium : BigDecimal.ZERO;
        
        // Set default descriptions based on coverage type
        if ("LIABILITY".equals(coverageType)) {
            this.limit1Description = "Per Person";
            this.limit2Description = "Per Accident";
            this.isRequired = true;
            this.isStateRequired = true;
        } else if ("COLLISION".equals(coverageType)) {
            this.limit1Description = "Vehicle Value";
            this.deductibleType = "COLLISION";
        } else if ("COMPREHENSIVE".equals(coverageType)) {
            this.limit1Description = "Vehicle Value";
            this.deductibleType = "COMPREHENSIVE";
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Policy getPolicy() {
        return policy;
    }
    
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
    
    public String getCoverageType() {
        return coverageType;
    }
    
    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }
    
    public String getCoverageSubtype() {
        return coverageSubtype;
    }
    
    public void setCoverageSubtype(String coverageSubtype) {
        this.coverageSubtype = coverageSubtype;
    }
    
    public String getCoverageCode() {
        return coverageCode;
    }
    
    public void setCoverageCode(String coverageCode) {
        this.coverageCode = coverageCode;
    }
    
    public String getCoverageName() {
        return coverageName;
    }
    
    public void setCoverageName(String coverageName) {
        this.coverageName = coverageName;
    }
    
    public BigDecimal getLimit1Amount() {
        return limit1Amount;
    }
    
    public void setLimit1Amount(BigDecimal limit1Amount) {
        this.limit1Amount = limit1Amount;
    }
    
    public String getLimit1Description() {
        return limit1Description;
    }
    
    public void setLimit1Description(String limit1Description) {
        this.limit1Description = limit1Description;
    }
    
    public BigDecimal getLimit2Amount() {
        return limit2Amount;
    }
    
    public void setLimit2Amount(BigDecimal limit2Amount) {
        this.limit2Amount = limit2Amount;
    }
    
    public String getLimit2Description() {
        return limit2Description;
    }
    
    public void setLimit2Description(String limit2Description) {
        this.limit2Description = limit2Description;
    }
    
    public BigDecimal getDeductibleAmount() {
        return deductibleAmount;
    }
    
    public void setDeductibleAmount(BigDecimal deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }
    
    public String getDeductibleType() {
        return deductibleType;
    }
    
    public void setDeductibleType(String deductibleType) {
        this.deductibleType = deductibleType;
    }
    
    public BigDecimal getPremium() {
        return premium;
    }
    
    public void setPremium(BigDecimal premium) {
        this.premium = premium != null ? premium : BigDecimal.ZERO;
    }
    
    public Boolean getRequired() {
        return isRequired;
    }
    
    public void setRequired(Boolean required) {
        isRequired = required;
    }
    
    public Boolean getOptional() {
        return isOptional;
    }
    
    public void setOptional(Boolean optional) {
        isOptional = optional;
    }
    
    public Boolean getDefaultSelected() {
        return isDefaultSelected;
    }
    
    public void setDefaultSelected(Boolean defaultSelected) {
        isDefaultSelected = defaultSelected;
    }
    
    public Boolean getStateRequired() {
        return isStateRequired;
    }
    
    public void setStateRequired(Boolean stateRequired) {
        isStateRequired = stateRequired;
    }
    
    public Boolean getPrimary() {
        return isPrimary;
    }
    
    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
    
    public Boolean getExcess() {
        return isExcess;
    }
    
    public void setExcess(Boolean excess) {
        isExcess = excess;
    }
    
    public Boolean getUmbrella() {
        return isUmbrella;
    }
    
    public void setUmbrella(Boolean umbrella) {
        isUmbrella = umbrella;
    }
    
    public Boolean getRentalReimbursement() {
        return isRentalReimbursement;
    }
    
    public void setRentalReimbursement(Boolean rentalReimbursement) {
        isRentalReimbursement = rentalReimbursement;
    }
    
    public Boolean getRoadsideAssistance() {
        return isRoadsideAssistance;
    }
    
    public void setRoadsideAssistance(Boolean roadsideAssistance) {
        isRoadsideAssistance = roadsideAssistance;
    }
    
    public Boolean getMedicalPayments() {
        return isMedicalPayments;
    }
    
    public void setMedicalPayments(Boolean medicalPayments) {
        isMedicalPayments = medicalPayments;
    }
    
    public Boolean getPIP() {
        return isPIP;
    }
    
    public void setPIP(Boolean PIP) {
        isPIP = PIP;
    }
    
    public Boolean getUMPD() {
        return isUMPD;
    }
    
    public void setUMPD(Boolean UMPD) {
        isUMPD = UMPD;
    }
    
    public Boolean getUMBI() {
        return isUMBI;
    }
    
    public void setUMBI(Boolean UMBI) {
        isUMBI = UMBI;
    }
    
    public Boolean getCustom() {
        return isCustom;
    }
    
    public void setCustom(Boolean custom) {
        isCustom = custom;
    }
    
    public String getCustomDescription() {
        return customDescription;
    }
    
    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }
    
    public String getCoverageNotes() {
        return coverageNotes;
    }
    
    public void setCoverageNotes(String coverageNotes) {
        this.coverageNotes = coverageNotes;
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
    public String getDisplayName() {
        if (coverageName != null && !coverageName.trim().isEmpty()) {
            return coverageName;
        }
        
        // Generate a display name based on type and subtype
        StringBuilder displayName = new StringBuilder();
        
        if (coverageType != null) {
            displayName.append(coverageType.replace("_", " ").toLowerCase());
            
            if (coverageSubtype != null) {
                displayName.append(" - ").append(coverageSubtype.replace("_", " ").toLowerCase());
            }
            
            // Capitalize first letter
            if (displayName.length() > 0) {
                displayName.setCharAt(0, Character.toUpperCase(displayName.charAt(0)));
            }
        }
        
        return displayName.length() > 0 ? displayName.toString() : "Unnamed Coverage";
    }
    
    public String getDisplayLimits() {
        if (limit1Amount == null && limit2Amount == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        
        if (limit1Amount != null) {
            sb.append("$").append(limit1Amount.toPlainString());
            if (limit1Description != null) {
                sb.append(" ").append(limit1Description);
            }
        }
        
        if (limit2Amount != null) {
            if (sb.length() > 0) {
                sb.append(" / ");
            }
            sb.append("$").append(limit2Amount.toPlainString());
            if (limit2Description != null) {
                sb.append(" ").append(limit2Description);
            }
        }
        
        return sb.toString();
    }
    
    public String getDisplayDeductible() {
        if (deductibleAmount == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder("$").append(deductibleAmount.toPlainString());
        
        if (deductibleType != null) {
            sb.append(" ").append(deductibleType.toLowerCase().replace("_", " "));
        } else if (coverageType != null) {
            sb.append(" ").append(coverageType.toLowerCase());
        }
        
        return sb.toString();
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        
        // Set default values
        if (this.premium == null) {
            this.premium = BigDecimal.ZERO;
        }
        
        // Set flags based on coverage type
        if (this.coverageType != null) {
            switch (this.coverageType) {
                case "LIABILITY":
                    this.isRequired = true;
                    this.isStateRequired = true;
                    this.isPrimary = true;
                    break;
                case "UNINSURED_MOTORIST":
                case "UNDERINSURED_MOTORIST":
                    this.isStateRequired = true;
                    break;
                case "PIP":
                    this.isPIP = true;
                    this.isStateRequired = true;
                    break;
                case "RENTAL_REIMBURSEMENT":
                    this.isRentalReimbursement = true;
                    break;
                case "ROADSIDE_ASSISTANCE":
                    this.isRoadsideAssistance = true;
                    break;
            }
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
    
    @Override
    public String toString() {
        return "Coverage{" +
                "id=" + id +
                ", coverageType='" + coverageType + '\'' +
                ", coverageSubtype='" + coverageSubtype + '\'' +
                ", premium=" + premium +
                ", limit1Amount=" + limit1Amount +
                ", limit2Amount=" + limit2Amount +
                ", deductibleAmount=" + deductibleAmount +
                '}';
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}