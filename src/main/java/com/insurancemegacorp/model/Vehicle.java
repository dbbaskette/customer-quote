package com.insurancemegacorp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a vehicle in the insurance system.
 * Contains detailed information about a vehicle for insurance underwriting and rating.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "vehicle_id", nullable = false, unique = true)
    private String vehicleId;
    
    @Column(name = "vin", length = 17, unique = true)
    private String vin; // Vehicle Identification Number
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false, length = 50)
    private String make;
    
    @Column(nullable = false, length = 100)
    private String model;
    
    @Column(length = 100)
    private String trim;
    
    @Column(name = "body_style", length = 50)
    private String bodyStyle; // SEDAN, SUV, TRUCK, etc.
    
    @Column(name = "vehicle_type", length = 50)
    private String vehicleType; // PASSENGER, MOTORCYCLE, COMMERCIAL, etc.
    
    @Column(name = "fuel_type", length = 20)
    private String fuelType; // GAS, DIESEL, ELECTRIC, HYBRID, etc.
    
    @Column(name = "transmission_type", length = 20)
    private String transmissionType; // AUTOMATIC, MANUAL, CVT, etc.
    
    @Column(name = "engine_size")
    private Double engineSize; // in liters
    
    @Column(name = "cylinder_count")
    private Integer cylinderCount;
    
    @Column(name = "horsepower")
    private Integer horsepower;
    
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    
    @Column(name = "current_value")
    private BigDecimal currentValue;
    
    @Column(name = "annual_mileage")
    private Integer annualMileage;
    
    @Column(name = "primary_use", length = 20)
    private String primaryUse; // COMMUTE, BUSINESS, PLEASURE, FARM, etc.
    
    @Column(name = "miles_one_way")
    private Integer milesOneWay; // For commute distance
    
    @Column(name = "ownership_type", length = 20)
    private String ownershipType; // OWNED, FINANCED, LEASED, COMPANY_CAR
    
    @Column(name = "is_new_vehicle")
    private Boolean isNewVehicle = false;
    
    @Column(name = "has_anti_theft")
    private Boolean hasAntiTheft = false;
    
    @Column(name = "anti_theft_type", length = 50)
    private String antiTheftType; // ALARM, TRACKING, IMMOBILIZER, etc.
    
    @Column(name = "safety_features", length = 255)
    private String safetyFeatures; // Comma-separated list of safety features
    
    @Column(name = "is_convertible")
    private Boolean isConvertible = false;
    
    @Column(name = "is_high_performance")
    private Boolean isHighPerformance = false;

    @Column(name = "is_luxury")
    private Boolean isLuxury = false;
    
    @Column(name = "is_antique")
    private Boolean isAntique = false;
    
    @Column(name = "is_modified")
    private Boolean isModified = false;
    
    @Column(name = "modification_details", columnDefinition = "TEXT")
    private String modificationDetails;
    
    @Column(name = "color", length = 30)
    private String color;
    
    @Column(name = "license_plate", length = 20)
    private String licensePlate;
    
    @Column(name = "license_state", length = 2)
    private String licenseState;
    
    @Column(name = "garaging_zip")
    private String garagingZip; // Where the vehicle is primarily kept
    
    @Column(name = "garaging_address_same_as_customer")
    private Boolean garagingAddressSameAsCustomer = true;
    
    @Embedded
    private Address garagingAddress;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    // Legacy fields - kept for backward compatibility
    @Transient
    private String name; // Year + Make + Model

    // Default constructor for JPA
    public Vehicle() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    // Constructor for demo/legacy support
    public Vehicle(String vehicleId, int year, String make) {
        this();
        this.vehicleId = vehicleId;
        this.year = year;
        this.make = make;
        this.model = "Unknown";
        this.vin = "1HGCM82633A" + (100000 + (int)(Math.random() * 900000));
        this.trim = "Base";
        this.bodyStyle = "SEDAN";
        this.vehicleType = "PASSENGER";
        this.fuelType = "GAS";
        this.transmissionType = "AUTOMATIC";
        this.engineSize = 2.4;
        this.cylinderCount = 4;
        this.horsepower = 180;
        this.purchaseDate = LocalDate.now().minusYears(1);
        this.purchasePrice = new BigDecimal("25000.00");
        this.currentValue = new BigDecimal("22000.00");
        this.annualMileage = 12000;
        this.primaryUse = "COMMUTE";
        this.milesOneWay = 10;
        this.ownershipType = "OWNED";
        this.color = "SILVER";
        this.licensePlate = "ABC" + (1000 + (int)(Math.random() * 9000));
        this.licenseState = "CA";
        this.garagingZip = "94105";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getTrim() {
        return trim;
    }
    
    public void setTrim(String trim) {
        this.trim = trim;
    }
    
    public String getBodyStyle() {
        return bodyStyle;
    }
    
    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    
    public String getTransmissionType() {
        return transmissionType;
    }
    
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }
    
    public Double getEngineSize() {
        return engineSize;
    }
    
    public void setEngineSize(Double engineSize) {
        this.engineSize = engineSize;
    }
    
    public Integer getCylinderCount() {
        return cylinderCount;
    }
    
    public void setCylinderCount(Integer cylinderCount) {
        this.cylinderCount = cylinderCount;
    }
    
    public Integer getHorsepower() {
        return horsepower;
    }
    
    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }
    
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public BigDecimal getCurrentValue() {
        return currentValue;
    }
    
    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
    
    public Integer getAnnualMileage() {
        return annualMileage;
    }
    
    public void setAnnualMileage(Integer annualMileage) {
        this.annualMileage = annualMileage;
    }
    
    public String getPrimaryUse() {
        return primaryUse;
    }
    
    public void setPrimaryUse(String primaryUse) {
        this.primaryUse = primaryUse;
    }
    
    public Integer getMilesOneWay() {
        return milesOneWay;
    }
    
    public void setMilesOneWay(Integer milesOneWay) {
        this.milesOneWay = milesOneWay;
    }
    
    public String getOwnershipType() {
        return ownershipType;
    }
    
    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }
    
    public Boolean getNewVehicle() {
        return isNewVehicle;
    }
    
    public void setNewVehicle(Boolean newVehicle) {
        isNewVehicle = newVehicle;
    }
    
    public Boolean getHasAntiTheft() {
        return hasAntiTheft;
    }
    
    public void setHasAntiTheft(Boolean hasAntiTheft) {
        this.hasAntiTheft = hasAntiTheft;
    }
    
    public String getAntiTheftType() {
        return antiTheftType;
    }
    
    public void setAntiTheftType(String antiTheftType) {
        this.antiTheftType = antiTheftType;
    }
    
    public String getSafetyFeatures() {
        return safetyFeatures;
    }
    
    public void setSafetyFeatures(String safetyFeatures) {
        this.safetyFeatures = safetyFeatures;
    }
    
    public Boolean getConvertible() {
        return isConvertible;
    }
    
    public void setConvertible(Boolean convertible) {
        isConvertible = convertible;
    }
    
    public Boolean getHighPerformance() {
        return isHighPerformance;
    }
    
    public void setHighPerformance(Boolean highPerformance) {
        isHighPerformance = highPerformance;
    }

    public void setLuxury(Boolean luxury) {
        isLuxury = luxury;
    }

    public Boolean getLuxury() {
        return isLuxury;
    }
    
    public Boolean getAntique() {
        return isAntique;
    }
    
    public void setAntique(Boolean antique) {
        isAntique = antique;
    }
    
    public Boolean getModified() {
        return isModified;
    }
    
    public void setModified(Boolean modified) {
        isModified = modified;
    }
    
    public String getModificationDetails() {
        return modificationDetails;
    }
    
    public void setModificationDetails(String modificationDetails) {
        this.modificationDetails = modificationDetails;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public String getLicenseState() {
        return licenseState;
    }
    
    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }
    
    public String getGaragingZip() {
        return garagingZip;
    }
    
    public void setGaragingZip(String garagingZip) {
        this.garagingZip = garagingZip;
    }
    
    public Boolean getGaragingAddressSameAsCustomer() {
        return garagingAddressSameAsCustomer;
    }
    
    public void setGaragingAddressSameAsCustomer(Boolean garagingAddressSameAsCustomer) {
        this.garagingAddressSameAsCustomer = garagingAddressSameAsCustomer;
    }
    
    public Address getGaragingAddress() {
        return garagingAddress;
    }
    
    public void setGaragingAddress(Address garagingAddress) {
        this.garagingAddress = garagingAddress;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        return year + " " + make + " " + model;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Helper methods
    public int getVehicleAge() {
        return LocalDate.now().getYear() - year;
    }
    
    public boolean isEligibleForNewCarReplacement() {
        return getVehicleAge() <= 1 && isNewVehicle && !isHighPerformance;
    }
    
    public boolean isHighRiskVehicle() {
        return isHighPerformance || isConvertible || 
               (getVehicleAge() > 15 && !isAntique) ||
               (make != null && (make.equalsIgnoreCase("Ferrari") || 
                               make.equalsIgnoreCase("Lamborghini") || 
                               make.equalsIgnoreCase("Porsche") ||
                               make.equalsIgnoreCase("Tesla") && model != null && model.toLowerCase().contains("model s plaid")));
    }

    public boolean getHasAirbags() {
        return safetyFeatures != null && safetyFeatures.toLowerCase().contains("airbags");
    }

    public boolean getHasAntiLockBrakes() {
        return safetyFeatures != null && safetyFeatures.toLowerCase().contains("anti-lock brakes");
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleId='" + vehicleId + '\'' +
                ", vin='" + vin + '\'' +
                ", year=" + year +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
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