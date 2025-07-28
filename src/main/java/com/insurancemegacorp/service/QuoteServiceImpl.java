package com.insurancemegacorp.service;

import com.insurancemegacorp.model.Customer;
import com.insurancemegacorp.model.Vehicle;
import com.insurancemegacorp.model.QuoteRequest;
import com.insurancemegacorp.model.QuoteResponse;
import com.insurancemegacorp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QuoteServiceImpl implements QuoteService {
    // Demo/upgrade only: repository field and method (not used in main logic)
    @Autowired(required = false)
    private CustomerRepository customerRepository;

    // For demonstration: fetch a customer by name (not used)
    public Customer fetchCustomerFromDb(String name) {
        if (customerRepository != null) {
            return customerRepository.findByName(name);
        }
        return null;
    }

    @Override
    public QuoteResponse generateQuote(QuoteRequest quoteRequest) {
        // Build Customer and Vehicle from request
        Customer customer = new Customer(
                quoteRequest.getCustomerId(),
                quoteRequest.getCustomerName(),
                quoteRequest.getCustomerAge()
        );
        Vehicle vehicle = new Vehicle(
                quoteRequest.getVehicleId(),
                quoteRequest.getVehicleYear(),
                quoteRequest.getVehicleMake()
        );

        // Calculate coverages
        Map<String, Double> coverages = new HashMap<>();
        // Liability: Base $500, +$20 per year under 25, +$10 per year before 2015
        double liability = 500;
        if (customer.getAge() < 25) {
            liability += (25 - customer.getAge()) * 20;
        }
        if (vehicle.getYear() < 2015) {
            liability += (2015 - vehicle.getYear()) * 10;
        }
        // Cap liability
        if (liability > 1000) liability = 1000;
        coverages.put("liability", liability);

        // Collision: Base $300, +$50 if vehicle year > 2020, else +$20
        double collision = 300;
        if (vehicle.getYear() > 2020) {
            collision += 50;
        } else {
            collision += 20;
        }
        coverages.put("collision", collision);

        // Comprehensive: Base $200, +$30 if customer age < 30, else +$10
        double comprehensive = 200;
        if (customer.getAge() < 30) {
            comprehensive += 30;
        } else {
            comprehensive += 10;
        }
        coverages.put("comprehensive", comprehensive);

        // Expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date expirationDate = calendar.getTime();

        return new QuoteResponse(
                UUID.randomUUID().toString(),
                customer,
                vehicle,
                coverages,
                expirationDate
        );
    }
}
