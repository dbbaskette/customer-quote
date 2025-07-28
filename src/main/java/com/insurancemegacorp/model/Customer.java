package com.insurancemegacorp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the insurance system.
 * Contains personal information required for insurance underwriting.
 */
@Entity
@Table(name = "customers")
public class Customer implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerId;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private String ssn; // In real app, should be encrypted
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name = "driver_license_number", nullable = false)
    private String driverLicenseNumber;
    
    @Column(name = "driver_license_state", nullable = false, length = 2)
    private String driverLicenseState;
    
    @Column(name = "license_issue_date", nullable = false)
    private LocalDate licenseIssueDate;
    
    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;
    
    @Column(name = "marital_status", length = 20)
    private String maritalStatus; // SINGLE, MARRIED, DIVORCED, WIDOWED
    
    @Column(name = "occupation", length = 100)
    private String occupation;
    
    @Column(name = "years_licensed")
    private Integer yearsLicensed;
    
    @Column(name = "has_prior_insurance")
    private Boolean hasPriorInsurance = false;
    
    @Column(name = "prior_insurance_carrier", length = 100)
    private String priorInsuranceCarrier;
    
    @Column(name = "prior_insurance_years")
    private Integer priorInsuranceYears;
    
    @Column(name = "is_primary_policy_holder")
    private Boolean isPrimaryPolicyHolder = true;
    
    @Column(name = "has_dui")
    private Boolean hasDUI = false;
    
    @Column(name = "dui_count")
    private Integer duiCount = 0;
    
    @Column(name = "has_accidents")
    private Boolean hasAccidents = false;
    
    @Column(name = "accident_count")
    private Integer accidentCount = 0;
    
    @Column(name = "has_traffic_violations")
    private Boolean hasTrafficViolations = false;
    
    @Column(name = "violation_count")
    private Integer violationCount = 0;
    
    @Column(name = "credit_rating")
    private String creditRating; // EXCELLENT, GOOD, FAIR, POOR
    
    @Column(name = "is_good_student")
    private Boolean isGoodStudent = false;
    
    @Column(name = "is_retired")
    private Boolean isRetired = false;
    
    @Column(name = "is_student_away_at_school")
    private Boolean isStudentAwayAtSchool = false;
    
    @Column(name = "credit_bureau", length = 50)
    private String creditBureau;
    
    @Column(name = "credit_score")
    private Integer creditScore;
    
    @Column(name = "credit_report_date")
    private LocalDate creditReportDate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @ElementCollection
    @CollectionTable(name = "customer_claims", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "claim_id")
    private List<String> claims = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "customer_policies",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<Policy> policies = new ArrayList<>();
    
    @Column(name = "military_affiliation")
    private String militaryAffiliation; // NONE, ACTIVE, VETERAN, RESERVE
    
    @Embedded
    private Address address;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();
    
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    // Getters and Setters for new fields
    public String getCreditBureau() {
        return creditBureau;
    }
    
    public void setCreditBureau(String creditBureau) {
        this.creditBureau = creditBureau;
    }
    
    public Integer getCreditScore() {
        return creditScore;
    }
    
    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
    
    public String getCreditRating() {
        return creditRating;
    }
    
    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }
    
    public LocalDate getCreditReportDate() {
        return creditReportDate;
    }
    
    public void setCreditReportDate(LocalDate creditReportDate) {
        this.creditReportDate = creditReportDate;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public List<String> getClaims() {
        return claims;
    }
    
    public void setClaims(List<String> claims) {
        this.claims = claims;
    }
    
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    public List<Policy> getPolicies() {
        return policies;
    }
    
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
    
    @Override
    public boolean isActive() {
        return active;
    }
    
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // Legacy fields - kept for backward compatibility
    @Transient
    private String name; // First + Last Name
    
    @Transient
    private int age; // Derived from dateOfBirth

    // Default constructor for JPA
    public Customer() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    // Constructor for demo/legacy support
    public Customer(String customerId, String name, int age) {
        this();
        this.customerId = customerId;
        this.name = name;
        this.age = age;
        
        // Default values for demo
        this.firstName = name.split(" ")[0];
        this.lastName = name.split(" ").length > 1 ? name.split(" ")[1] : "";
        this.dateOfBirth = LocalDate.now().minusYears(age);
        this.ssn = "XXX-XX-XXXX";
        this.email = "demo@example.com";
        this.phoneNumber = "555-010-1234";
        this.driverLicenseNumber = "DL" + customerId;
        this.driverLicenseState = "CA";
        this.licenseIssueDate = LocalDate.now().minusYears(5);
        this.licenseExpiryDate = LocalDate.now().plusYears(5);
        this.maritalStatus = "SINGLE";
        this.occupation = "SOFTWARE_DEVELOPER";
        this.yearsLicensed = age - 16 > 0 ? age - 16 : 1;
        this.address = new Address("123 Main St", "Apt 4B", "San Francisco", "CA", "94105", "USA");
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getSsn() {
        return ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
    
    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }
    
    public String getDriverLicenseState() {
        return driverLicenseState;
    }
    
    public void setDriverLicenseState(String driverLicenseState) {
        this.driverLicenseState = driverLicenseState;
    }
    
    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }
    
    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }
    
    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }
    
    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }
    
    public String getMaritalStatus() {
        return maritalStatus;
    }
    
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public Integer getYearsLicensed() {
        return yearsLicensed;
    }
    
    public void setYearsLicensed(Integer yearsLicensed) {
        this.yearsLicensed = yearsLicensed;
    }
    
    public Boolean getHasPriorInsurance() {
        return hasPriorInsurance;
    }
    
    public void setHasPriorInsurance(Boolean hasPriorInsurance) {
        this.hasPriorInsurance = hasPriorInsurance;
    }
    
    public String getPriorInsuranceCarrier() {
        return priorInsuranceCarrier;
    }
    
    public void setPriorInsuranceCarrier(String priorInsuranceCarrier) {
        this.priorInsuranceCarrier = priorInsuranceCarrier;
    }
    
    public Integer getPriorInsuranceYears() {
        return priorInsuranceYears;
    }
    
    public void setPriorInsuranceYears(Integer priorInsuranceYears) {
        this.priorInsuranceYears = priorInsuranceYears;
    }
    
    public Boolean getPrimaryPolicyHolder() {
        return isPrimaryPolicyHolder;
    }
    
    public void setPrimaryPolicyHolder(Boolean primaryPolicyHolder) {
        isPrimaryPolicyHolder = primaryPolicyHolder;
    }
    
    public Boolean getHasDUI() {
        return hasDUI;
    }
    
    public void setHasDUI(Boolean hasDUI) {
        this.hasDUI = hasDUI;
    }
    
    public Integer getDuiCount() {
        return duiCount;
    }
    
    public void setDuiCount(Integer duiCount) {
        this.duiCount = duiCount;
    }
    
    public Boolean getHasAccidents() {
        return hasAccidents;
    }
    
    public void setHasAccidents(Boolean hasAccidents) {
        this.hasAccidents = hasAccidents;
    }
    
    public Integer getAccidentCount() {
        return accidentCount;
    }
    
    public void setAccidentCount(Integer accidentCount) {
        this.accidentCount = accidentCount;
    }
    
    public Boolean getHasTrafficViolations() {
        return hasTrafficViolations;
    }
    
    public void setHasTrafficViolations(Boolean hasTrafficViolations) {
        this.hasTrafficViolations = hasTrafficViolations;
    }
    
    public Integer getViolationCount() {
        return violationCount;
    }
    
    public void setViolationCount(Integer violationCount) {
        this.violationCount = violationCount;
    }
    
    public Boolean getGoodStudent() {
        return isGoodStudent;
    }
    
    public void setGoodStudent(Boolean goodStudent) {
        isGoodStudent = goodStudent;
    }
    
    public Boolean getRetired() {
        return isRetired;
    }
    
    public void setRetired(Boolean retired) {
        isRetired = retired;
    }
    
    public Boolean getStudentAwayAtSchool() {
        return isStudentAwayAtSchool;
    }
    
    public void setStudentAwayAtSchool(Boolean studentAwayAtSchool) {
        isStudentAwayAtSchool = studentAwayAtSchool;
    }
    
    public String getMilitaryAffiliation() {
        return militaryAffiliation;
    }
    
    public void setMilitaryAffiliation(String militaryAffiliation) {
        this.militaryAffiliation = militaryAffiliation;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
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
    
    // Legacy getters/setters for backward compatibility
    public String getName() {
        return name != null ? name : (firstName + " " + lastName).trim();
    }
    
    public void setName(String name) {
        this.name = name;
        if (name != null) {
            String[] parts = name.split(" ", 2);
            this.firstName = parts[0];
            this.lastName = parts.length > 1 ? parts[1] : "";
        }
    }
    
    public int getAge() {
        if (dateOfBirth != null) {
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        }
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
        if (this.dateOfBirth == null) {
            this.dateOfBirth = LocalDate.now().minusYears(age);
        }
    }
}