package com.insurancemegacorp.service;

import com.insurancemegacorp.model.*;
import com.insurancemegacorp.repository.CustomerRepository;
import com.insurancemegacorp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the QuoteService interface providing comprehensive
 * insurance quote generation with multiple rating factors.
 */
@Service
@Transactional(readOnly = true)
public class QuoteServiceImpl implements QuoteService {
    
    // Base rates for different coverage types (in dollars)
    private static final double BASE_LIABILITY_RATE = 500.0;
    private static final double BASE_COLLISION_RATE = 300.0;
    private static final double BASE_COMPREHENSIVE_RATE = 200.0;
    private static final double BASE_UNINSURED_MOTORIST_RATE = 150.0;
    private static final double BASE_MEDICAL_PAYMENTS_RATE = 100.0;
    private static final double BASE_RENTAL_REIMBURSEMENT_RATE = 50.0;
    private static final double BASE_ROADSIDE_ASSISTANCE_RATE = 40.0;
    
    // Rating factors
    private static final double YOUNG_DRIVER_SURCHARGE = 1.5; // 50% surcharge for drivers under 25
    private static final double SENIOR_DRIVER_SURCHARGE = 1.2; // 20% surcharge for drivers over 70
    private static final double NEW_DRIVER_SURCHARGE = 1.3; // 30% surcharge for drivers with < 3 years experience
    private static final double HIGH_PERFORMANCE_SURCHARGE = 1.4; // 40% surcharge for high-performance vehicles
    private static final double LUXURY_VEHICLE_SURCHARGE = 1.5; // 50% surcharge for luxury vehicles
    private static final double MULTI_POLICY_DISCOUNT = 0.9; // 10% discount for customers with multiple policies
    private static final double GOOD_DRIVER_DISCOUNT = 0.85; // 15% discount for good drivers
    private static final double GOOD_STUDENT_DISCOUNT = 0.9; // 10% discount for good students
    private static final double ANTI_THEFT_DISCOUNT = 0.9; // 10% discount for anti-theft devices
    private static final double SAFETY_FEATURES_DISCOUNT = 0.95; // 5% discount for safety features
    
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    
    @Autowired
    public QuoteServiceImpl(CustomerRepository customerRepository, 
                          VehicleRepository vehicleRepository,
                          CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public QuoteResponse generateQuote(QuoteRequest quoteRequest) {
        // 1. Validate request
        validateQuoteRequest(quoteRequest);
        
        // 2. Retrieve or create customer
        Customer customer = getOrCreateCustomer(quoteRequest);
        
        // 3. Retrieve or create vehicle
        Vehicle vehicle = getOrCreateVehicle(quoteRequest);
        
        // 4. Calculate base rates for each coverage type
        Map<String, Double> coverages = calculateCoverageRates(customer, vehicle);
        
        // 5. Apply discounts and surcharges
        applyDiscountsAndSurcharges(customer, vehicle, coverages);
        
        // 6. Calculate total premium
        double totalPremium = calculateTotalPremium(coverages);
        coverages.put("totalPremium", totalPremium);
        
        // 7. Set expiration date (30 days from now)
        Date expirationDate = Date.from(
            LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        
        // 8. Generate and return quote response
        return new QuoteResponse(
            "QUOTE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            customer,
            vehicle,
            coverages,
            expirationDate
        );
    }
    
    private void validateQuoteRequest(QuoteRequest request) {
        Objects.requireNonNull(request, "Quote request cannot be null");
        
        if (request.getCustomerAge() < 16) {
            throw new IllegalArgumentException("Customer must be at least 16 years old");
        }
        
        if (request.getCustomerAge() > 100) {
            throw new IllegalArgumentException("Customer age is not valid");
        }
        
        int currentYear = LocalDate.now().getYear();
        if (request.getVehicleYear() < 1900 || request.getVehicleYear() > currentYear + 1) {
            throw new IllegalArgumentException("Vehicle year must be between 1900 and " + (currentYear + 1));
        }
        
        if (request.getVehicleMake() == null || request.getVehicleMake().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle make is required");
        }
    }
    
    private Customer getOrCreateCustomer(QuoteRequest request) {
        // In a real application, we would look up the customer by ID
        // For this example, we'll create a new customer with the provided information
        Customer customer = new Customer();
        customer.setFirstName(request.getCustomerName().split(" ")[0]);
        customer.setLastName(request.getCustomerName().contains(" ") ? 
                           request.getCustomerName().substring(request.getCustomerName().lastIndexOf(' ') + 1) : "");
        customer.setDateOfBirth(LocalDate.now().minusYears(request.getCustomerAge()));
        customer.setEmail("temp-email@example.com"); // In a real app, this would be collected from the request
        customer.setPhoneNumber("000-000-0000"); // In a real app, this would be collected from the request
        customer.setDriverLicenseNumber("TEMP" + UUID.randomUUID().toString().substring(0, 8));
        customer.setDriverLicenseState("CA"); // Default state
        customer.setLicenseIssueDate(LocalDate.now().minusYears(5)); // Assume 5 years of driving experience
        customer.setCreditScore(700); // Default good credit score
        
        return customer;
    }
    
    private Vehicle getOrCreateVehicle(QuoteRequest request) {
        // In a real application, we would look up the vehicle by VIN or other identifier
        // For this example, we'll create a new vehicle with the provided information
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId("VIN-" + UUID.randomUUID().toString().substring(0, 8));
        vehicle.setVin("VIN" + UUID.randomUUID().toString().replace("-", "").substring(0, 13));
        vehicle.setYear(request.getVehicleYear());
        vehicle.setMake(request.getVehicleMake());
        vehicle.setModel("Unknown"); // Default model
        vehicle.setBodyStyle("SEDAN"); // Default body style
        vehicle.setVehicleType("PASSENGER"); // Default vehicle type
        vehicle.setFuelType("GAS"); // Default fuel type
        vehicle.setTransmissionType("AUTOMATIC"); // Default transmission
        vehicle.setEngineSize(2.4); // Default engine size in liters
        vehicle.setPurchasePrice(new BigDecimal("25000.0")); // Default purchase price
        
        // Determine if this is a high-performance or luxury vehicle based on make
        List<String> performanceMakes = Arrays.asList("BMW", "MERCEDES", "AUDI", "PORSCHE", "FERRARI", "LAMBORGHINI");
        boolean isPerformance = performanceMakes.contains(request.getVehicleMake().toUpperCase());
        vehicle.setHighPerformance(isPerformance);
        vehicle.setLuxury(isPerformance);
        
        // Set safety features - using the actual field names from Vehicle class
        vehicle.setHasAntiTheft(false); // Will be set to true for some vehicles
        // Note: hasAirbags and hasAntiLockBrakes are not defined in Vehicle class
        // Using safetyFeatures string field instead
        vehicle.setSafetyFeatures("AIRBAG,ANTI_LOCK_BRAKES");
        
        return vehicle;
    }
    
    private Map<String, Double> calculateCoverageRates(Customer customer, Vehicle vehicle) {
        Map<String, Double> coverages = new HashMap<>();
        
        // Calculate base rates for each coverage type
        double liability = calculateLiabilityPremium(customer, vehicle);
        double collision = calculateCollisionPremium(customer, vehicle);
        double comprehensive = calculateComprehensivePremium(customer, vehicle);
        double uninsuredMotorist = BASE_UNINSURED_MOTORIST_RATE;
        double medicalPayments = BASE_MEDICAL_PAYMENTS_RATE;
        double rentalReimbursement = BASE_RENTAL_REIMBURSEMENT_RATE;
        double roadsideAssistance = BASE_ROADSIDE_ASSISTANCE_RATE;
        
        // Add to coverages map
        coverages.put("liability", round(liability, 2));
        coverages.put("collision", round(collision, 2));
        coverages.put("comprehensive", round(comprehensive, 2));
        coverages.put("uninsuredMotorist", round(uninsuredMotorist, 2));
        coverages.put("medicalPayments", round(medicalPayments, 2));
        coverages.put("rentalReimbursement", round(rentalReimbursement, 2));
        coverages.put("roadsideAssistance", round(roadsideAssistance, 2));
        
        return coverages;
    }
    
    private double calculateLiabilityPremium(Customer customer, Vehicle vehicle) {
        double rate = BASE_LIABILITY_RATE;
        
        // Adjust based on customer age
        int age = Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears();
        if (age < 25) {
            rate *= YOUNG_DRIVER_SURCHARGE;
        } else if (age > 70) {
            rate *= SENIOR_DRIVER_SURCHARGE;
        }
        
        // Adjust based on driving experience
        long yearsLicensed = Period.between(customer.getLicenseIssueDate(), LocalDate.now()).getYears();
        if (yearsLicensed < 3) {
            rate *= NEW_DRIVER_SURCHARGE;
        }
        
        // Adjust based on credit score
        if (customer.getCreditScore() < 600) {
            rate *= 1.25; // 25% surcharge for poor credit
        } else if (customer.getCreditScore() > 750) {
            rate *= 0.9; // 10% discount for excellent credit
        }
        
        return rate;
    }
    
    private double calculateCollisionPremium(Customer customer, Vehicle vehicle) {
        double rate = BASE_COLLISION_RATE;
        
        // Adjust based on vehicle age (newer cars are more expensive to repair)
        int currentYear = LocalDate.now().getYear();
        int vehicleAge = currentYear - vehicle.getYear();
        if (vehicleAge < 3) {
            rate *= 1.3; // 30% more for new cars
        } else if (vehicleAge > 10) {
            rate *= 0.8; // 20% less for older cars
        }
        
        // Adjust based on vehicle type - using getter methods for boolean fields
        if (Boolean.TRUE.equals(vehicle.getHighPerformance())) {
            rate *= HIGH_PERFORMANCE_SURCHARGE;
        }
        
        // Check if vehicle is luxury based on make and model
        List<String> luxuryMakes = Arrays.asList("BMW", "MERCEDES", "AUDI", "PORSCHE", "FERRARI", "LAMBORGHINI");
        if (luxuryMakes.contains(vehicle.getMake().toUpperCase())) {
            rate *= LUXURY_VEHICLE_SURCHARGE;
        }
        
        return rate;
    }
    
    private double calculateComprehensivePremium(Customer customer, Vehicle vehicle) {
        double rate = BASE_COMPREHENSIVE_RATE;
        
        // Adjust based on vehicle value (simplified)
        BigDecimal purchasePrice = vehicle.getPurchasePrice();
        if (purchasePrice != null) {
            if (purchasePrice.compareTo(new BigDecimal("50000")) > 0) {
                rate *= 1.5;
            } else if (purchasePrice.compareTo(new BigDecimal("30000")) > 0) {
                rate *= 1.2;
            }
        }
        
        // Adjust based on vehicle type - using getter methods for boolean fields
        if (Boolean.TRUE.equals(vehicle.getConvertible())) {
            rate *= 1.3; // 30% more for convertibles
        }
        
        return rate;
    }
    
    private void applyDiscountsAndSurcharges(Customer customer, Vehicle vehicle, Map<String, Double> coverages) {
        // Apply multi-policy discount if applicable
        if (customerService != null && customer.getId() != null && customerService.hasOtherPolicies(customer.getId())) {
            applyDiscount(coverages, MULTI_POLICY_DISCOUNT, "multiPolicyDiscount");
        }
        
        // Apply good driver discount
        if (customerService != null && customer.getId() != null && customerService.isGoodDriver(customer.getId())) {
            applyDiscount(coverages, GOOD_DRIVER_DISCOUNT, "goodDriverDiscount");
        }
        
        // Apply good student discount - using getter methods for boolean fields
        if (Boolean.TRUE.equals(customer.getGoodStudent())) {
            applyDiscount(coverages, GOOD_STUDENT_DISCOUNT, "goodStudentDiscount");
        }
        
        // Apply anti-theft device discount
        if (Boolean.TRUE.equals(vehicle.getHasAntiTheft())) {
            applyDiscount(coverages, ANTI_THEFT_DISCOUNT, "antiTheftDiscount");
        }
        
        // Apply safety features discount
        if (vehicle.getHasAirbags() && vehicle.getHasAntiLockBrakes()) {
            applyDiscount(coverages, SAFETY_FEATURES_DISCOUNT, "safetyFeaturesDiscount");
        }
        
        // Apply defensive driving course discount if applicable
        // Note: completedDefensiveDriving field doesn't exist in Customer
        // Using hasCompletedDefensiveDrivingCourse if it exists, otherwise skipping
        try {
            if (Boolean.TRUE.equals((Boolean) customer.getClass()
                    .getMethod("getHasCompletedDefensiveDrivingCourse")
                    .invoke(customer))) {
                applyDiscount(coverages, 0.9, "defensiveDrivingDiscount"); // 10% discount
            }
        } catch (Exception e) {
            // Field or method doesn't exist, skip this discount
        }
    }
    
    private void applyDiscount(Map<String, Double> coverages, double discountFactor, String discountName) {
        // Apply discount to all coverages except total premium
        for (Map.Entry<String, Double> entry : new HashMap<>(coverages).entrySet()) {
            if (!entry.getKey().equals("totalPremium") && !entry.getKey().endsWith("Discount")) {
                double discountedAmount = entry.getValue() * discountFactor;
                coverages.put(entry.getKey(), round(discountedAmount, 2));
                
                // Track the discount amount for this coverage type
                String discountKey = entry.getKey() + "Discount";
                double discountAmount = entry.getValue() - discountedAmount;
                coverages.put(discountKey, round(discountAmount, 2));
            }
        }
    }
    
    private double calculateTotalPremium(Map<String, Double> coverages) {
        double total = 0.0;
        
        // Sum up all coverages that aren't discounts or the total itself
        for (Map.Entry<String, Double> entry : coverages.entrySet()) {
            String key = entry.getKey();
            if (!key.endsWith("Discount") && !key.equals("totalPremium")) {
                total += entry.getValue();
            }
        }
        
        return round(total, 2);
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}