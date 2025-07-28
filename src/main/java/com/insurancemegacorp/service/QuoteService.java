package com.insurancemegacorp.service;

import com.insurancemegacorp.model.QuoteRequest;
import com.insurancemegacorp.model.QuoteResponse;

public interface QuoteService {
    QuoteResponse generateQuote(QuoteRequest quoteRequest);
}
