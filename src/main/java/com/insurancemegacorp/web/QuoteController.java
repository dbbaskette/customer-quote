package com.insurancemegacorp.web;

import com.insurancemegacorp.model.QuoteRequest;
import com.insurancemegacorp.model.QuoteResponse;
import com.insurancemegacorp.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/quote")
    // Returns a QuoteResponse with customer, vehicle, and coverages (liability, collision, comprehensive)
    public QuoteResponse getQuote(@RequestBody QuoteRequest quoteRequest) {
        return quoteService.generateQuote(quoteRequest);
    }
}
