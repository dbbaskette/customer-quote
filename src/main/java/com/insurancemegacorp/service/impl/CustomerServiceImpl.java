package com.insurancemegacorp.service.impl;

import com.insurancemegacorp.exception.ResourceNotFoundException;
import com.insurancemegacorp.model.Address;
import com.insurancemegacorp.model.Customer;
import com.insurancemegacorp.model.Policy;
import com.insurancemegacorp.model.Vehicle;
import com.insurancemegacorp.repository.CustomerRepository;
import com.insurancemegacorp.repository.PolicyRepository;
import com.insurancemegacorp.repository.VehicleRepository;
import com.insurancemegacorp.service.BaseServiceImpl;
import com.insurancemegacorp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the CustomerService interface.
 */
@Service
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long> implements CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final PolicyRepository policyRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, VehicleRepository vehicleRepository, PolicyRepository policyRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByEmail(String email) {
        validateEmail(email);
        return customerRepository.findByEmail(email.toLowerCase());
    }

    // Additional helper methods for validation
    private void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email must not be empty");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByLastName(String lastName) {
        if (!StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("Last name must not be empty");
        }
        return customerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return customerRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates must not be null");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }

    // Additional service methods will be implemented in subsequent chunks
    // to avoid timeout issues
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByDriverLicenseNumberAndLicenseState(String driverLicenseNumber, String licenseState) {
        validateDriverLicense(driverLicenseNumber, licenseState);
        return customerRepository.findByDriverLicenseNumberAndLicenseState(
            driverLicenseNumber, 
            licenseState.toUpperCase()
        );
    }

    private void validateDriverLicense(String number, String state) {
        if (!StringUtils.hasText(number)) {
            throw new IllegalArgumentException("Driver license number must not be empty");
        }
        if (!StringUtils.hasText(state)) {
            throw new IllegalArgumentException("License state must not be empty");
        }
    }
    
    // Additional methods will be implemented in the next chunk
    
    @Override
    @Transactional
    public Customer updateEmail(Long customerId, String newEmail) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        validateEmail(newEmail);
        
        if (existsByEmail(newEmail)) {
            throw new IllegalStateException("Email " + newEmail + " is already in use");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        customer.setEmail(newEmail.toLowerCase());
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    private Customer getCustomerOrThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByCreditRatingBetween(int minScore, int maxScore) {
        validateCreditScoreRange(minScore, maxScore);
        return customerRepository.findByCreditRatingBetween(minScore, maxScore);
    }
    
    private void validateCreditScoreRange(int minScore, int maxScore) {
        if (minScore < 300 || maxScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
        if (minScore > maxScore) {
            throw new IllegalArgumentException("Minimum score must be less than or equal to maximum score");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findByCity(String city, Pageable pageable) {
        if (!StringUtils.hasText(city)) {
            throw new IllegalArgumentException("City must not be empty");
        }
        return customerRepository.findByCity(city, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findByState(String state, Pageable pageable) {
        validateStateCode(state);
        return customerRepository.findByState(state.toUpperCase(), pageable);
    }
    
    private void validateStateCode(String state) {
        if (!StringUtils.hasText(state) || state.length() != 2) {
            throw new IllegalArgumentException("State must be a 2-letter code");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findByPostalCode(String postalCode, Pageable pageable) {
        if (!StringUtils.hasText(postalCode)) {
            throw new IllegalArgumentException("Postal code must not be empty");
        }
        return customerRepository.findByPostalCode(postalCode, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        validateEmail(email);
        return customerRepository.existsByEmailAndActiveTrue(email.toLowerCase());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByDriverLicense(String driverLicenseNumber, String licenseState) {
        validateDriverLicense(driverLicenseNumber, licenseState);
        return customerRepository.existsByDriverLicenseNumberAndLicenseStateAndActiveTrue(
            driverLicenseNumber, 
            licenseState.toUpperCase()
        );
    }
    
    @Override
    @Transactional
    public Customer updateAddress(Long customerId, Address newAddress) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        if (newAddress == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Create a new address object to avoid modifying the input parameter
        Address address = new Address();
        address.setStreet1(newAddress.getStreet1());
        address.setStreet2(newAddress.getStreet2());
        address.setCity(newAddress.getCity());
        address.setState(newAddress.getState());
        address.setPostalCode(newAddress.getPostalCode());
        address.setCountry(newAddress.getCountry());
        
        customer.setAddress(address);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer updateDriverLicenseInfo(
        Long customerId, 
        String driverLicenseNumber, 
        String licenseState, 
        LocalDate licenseExpiryDate
    ) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        validateDriverLicense(driverLicenseNumber, licenseState);
        
        if (licenseExpiryDate == null || licenseExpiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("License expiry date must be in the future");
        }
        
        // Check if the driver's license is already in use by another customer
        Optional<Customer> existingCustomer = customerRepository
            .findByDriverLicenseNumberAndLicenseState(driverLicenseNumber, licenseState);
            
        if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(customerId)) {
            throw new IllegalStateException("Driver license number " + driverLicenseNumber + 
                " is already in use by another customer");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        customer.setDriverLicenseNumber(driverLicenseNumber);
        customer.setDriverLicenseState(licenseState.toUpperCase());
        customer.setLicenseExpiryDate(licenseExpiryDate);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer updateCreditRating(
        Long customerId,
        String creditBureau,
        Integer creditScore,
        String creditRating,
        LocalDate reportDate
    ) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        validateCreditBureau(creditBureau);
        validateCreditScore(creditScore);
        
        if (!StringUtils.hasText(creditRating)) {
            throw new IllegalArgumentException("Credit rating must not be empty");
        }
        
        if (reportDate == null || reportDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Report date must be in the past");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        customer.setCreditBureau(creditBureau);
        customer.setCreditScore(creditScore);
        customer.setCreditRating(creditRating);
        customer.setCreditReportDate(reportDate);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    private void validateCreditBureau(String creditBureau) {
        if (!StringUtils.hasText(creditBureau)) {
            throw new IllegalArgumentException("Credit bureau must not be empty");
        }
        
        // Validate against known credit bureaus
        List<String> validBureaus = List.of("EXPERIAN", "EQUIFAX", "TRANSUNION");
        if (!validBureaus.contains(creditBureau.toUpperCase())) {
            throw new IllegalArgumentException("Invalid credit bureau. Must be one of: " + validBureaus);
        }
    }
    
    private void validateCreditScore(Integer creditScore) {
        if (creditScore == null || creditScore < 300 || creditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
    }
    
    @Override
    @Transactional
    public Customer addClaim(Long customerId, String claimId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        if (!StringUtils.hasText(claimId)) {
            throw new IllegalArgumentException("Claim ID must not be empty");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Initialize claims list if null
        if (customer.getClaims() == null) {
            customer.setClaims(new ArrayList<>());
        }
        
        // Check if claim already exists
        if (customer.getClaims().contains(claimId)) {
            throw new IllegalStateException("Claim with ID " + claimId + " already exists for customer " + customerId);
        }
        
        customer.getClaims().add(claimId);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer removeClaim(Long customerId, String claimId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        if (!StringUtils.hasText(claimId)) {
            throw new IllegalArgumentException("Claim ID must not be empty");
        }
        
        Customer customer = getCustomerOrThrow(customerId);
        
        if (customer.getClaims() == null || !customer.getClaims().contains(claimId)) {
            throw new ResourceNotFoundException("Claim with ID " + claimId + " not found for customer " + customerId);
        }
        
        customer.getClaims().remove(claimId);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findCustomersWithActivePolicies(Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable must not be null");
        return customerRepository.findCustomersWithActivePolicies(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findCustomersWithExpiredPolicies(Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable must not be null");
        return customerRepository.findCustomersWithExpiredPolicies(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findCustomersWithRecentClaims(int months, Pageable pageable) {
        if (months <= 0) {
            throw new IllegalArgumentException("Months must be greater than 0");
        }
        Objects.requireNonNull(pageable, "Pageable must not be null");
        
        LocalDate cutoffDate = LocalDate.now().minusMonths(months);
        return customerRepository.findCustomersWithRecentClaims(cutoffDate, pageable);
    }
    
    @Override
    @Transactional
    public Customer addVehicle(Long customerId, Long vehicleId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        Objects.requireNonNull(vehicleId, "Vehicle ID must not be null");
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Check if vehicle exists and is active
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));
        
        // Initialize vehicles list if null
        if (customer.getVehicles() == null) {
            customer.setVehicles(new ArrayList<>());
        }
        
        // Check if vehicle is already associated with the customer
        if (customer.getVehicles().contains(vehicle)) {
            throw new IllegalStateException("Vehicle " + vehicleId + " is already associated with customer " + customerId);
        }
        
        customer.getVehicles().add(vehicle);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer removeVehicle(Long customerId, Long vehicleId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        Objects.requireNonNull(vehicleId, "Vehicle ID must not be null");
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Find the vehicle in the customer's vehicles
        Vehicle vehicleToRemove = customer.getVehicles().stream()
            .filter(v -> v.getId().equals(vehicleId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle " + vehicleId + " not found for customer " + customerId));
        
        customer.getVehicles().remove(vehicleToRemove);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer addPolicy(Long customerId, Long policyId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        Objects.requireNonNull(policyId, "Policy ID must not be null");
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Check if policy exists and is active
        Policy policy = policyRepository.findById(policyId)
            .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + policyId));
        
        // Initialize policies list if null
        if (customer.getPolicies() == null) {
            customer.setPolicies(new ArrayList<>());
        }
        
        // Check if policy is already associated with the customer
        if (customer.getPolicies().contains(policy)) {
            throw new IllegalStateException("Policy " + policyId + " is already associated with customer " + customerId);
        }
        
        customer.getPolicies().add(policy);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }
    
    @Override
    @Transactional
    public Customer removePolicy(Long customerId, Long policyId) {
        Objects.requireNonNull(customerId, "Customer ID must not be null");
        Objects.requireNonNull(policyId, "Policy ID must not be null");
        
        Customer customer = getCustomerOrThrow(customerId);
        
        // Find the policy in the customer's policies
        Policy policyToRemove = customer.getPolicies().stream()
            .filter(p -> p.getId().equals(policyId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Policy " + policyId + " not found for customer " + customerId));
        
        customer.getPolicies().remove(policyToRemove);
        customer.setLastUpdated(LocalDateTime.now());
        
        return save(customer);
    }

    @Override
    public boolean isGoodDriver(Long customerId) {
        Customer customer = getCustomerOrThrow(customerId);
        return !customer.getHasDUI() && customer.getAccidentCount() == 0 && customer.getViolationCount() <= 1;
    }

    @Override
    public boolean hasOtherPolicies(Long customerId) {
        Customer customer = getCustomerOrThrow(customerId);
        return customer.getPolicies() != null && !customer.getPolicies().isEmpty();
    }

    @Override
    public void hardDelete(Customer entity) {
        customerRepository.delete(entity);
    }

    @Override
    public void hardDeleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void hardDeleteAll() {
        customerRepository.deleteAll();
    }

    @Override
    public void hardDeleteAll(Iterable<? extends Customer> entities) {
        customerRepository.deleteAll(entities);
    }
}
