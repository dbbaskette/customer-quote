# implementation_details.md

## Quote Generation Logic

- The quote generation service will accept a QuoteRequest containing customer and vehicle information.
- The QuoteResponse will include:
  - Customer details (mirrored from request)
  - Vehicle details (mirrored from request)
  - Coverages: liability, collision, comprehensive

## Coverage Calculation (Demo Business Rules)
- Liability: Base rate $500, adjusted by driver's age and vehicle year
- Collision: Base rate $300, adjusted by vehicle value
- Comprehensive: Base rate $200, adjusted by location (if available)
- All calculations are for demo purposes and should be verbose and easy to follow in code.

## Response Structure
- QuoteResponse will be expanded to include a list or map of coverages with their calculated amounts.
- Example:
  {
    "customer": { ... },
    "vehicle": { ... },
    "coverages": {
      "liability": 600,
      "collision": 350,
      "comprehensive": 220
    }
  }

## Edge Cases
- If required fields are missing, return a validation error.
- If vehicle year or value is unreasonable, cap adjustments.

## Next Steps
- Update QuoteResponse model
- Implement coverage calculation logic in QuoteService
- Update controller to return expanded response 

## Demo/Upgrade-Only Database Integration
- Customer and Vehicle are annotated as JPA entities, and repositories are present.
- No datasource is configured; these are for demonstration and upgrade scenarios only.
- The main quote logic does not use the database or repositories.
- Example fetch methods exist in the service layer but are not called. 