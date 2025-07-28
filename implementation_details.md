# Implementation Details

## Domain Models

### 1. Customer
Represents a policyholder or potential customer with comprehensive personal and insurance-related information.

#### Key Fields:
- **Personal Information**: firstName, lastName, dateOfBirth, ssn, email, phone
- **Driver Information**: driverLicenseNumber, licenseState, licenseIssueDate, licenseExpirationDate, licenseClass
- **Demographics**: maritalStatus, occupation, educationLevel, employer, yearsAtEmployer
- **Insurance History**: priorInsuranceCarrier, priorPolicyNumber, priorInsuranceExpiration, priorInsuranceYears, isPriorInsuranceVerified
- **Risk Factors**: hasAccidents, accidentCount, hasViolations, violationCount, hasDUIs, duiCount, creditRating
- **Addresses**: Embedded Address object for current and previous addresses
- **Relationships**: One-to-many with Vehicle
- **Timestamps**: createdAt, updatedAt

### 2. Vehicle
Represents a vehicle to be insured with detailed specifications and usage information.

#### Key Fields:
- **Identification**: vehicleId, VIN, year, make, model, trim, bodyStyle, vehicleType
- **Specifications**: fuelType, transmission, engineSize, cylinders, horsepower, driveType
- **Ownership & Usage**: purchaseDate, purchasePrice, currentValue, annualMileage, primaryUse, ownershipType
- **Safety & Features**: antiTheftDevice, safetyFeatures, hasAirbags, hasABS, hasTractionControl, hasBackupCamera
- **Special Attributes**: isConvertible, isHighPerformance, isAntique, isModified, isRideshare
- **Registration**: licensePlate, licenseState, registrationExpiration
- **Garaging**: Embedded Address for garaging location
- **Relationships**: Many-to-one with Customer, Many-to-many with Policy
- **Timestamps**: createdAt, updatedAt

### 3. Policy
Represents an insurance policy containing coverages, terms, and premium information.

#### Key Fields:
- **Identification**: policyNumber, policyType, policyStatus
- **Term**: effectiveDate, expirationDate, issueDate, bindDate, cancellationDate, termLengthMonths
- **Financials**: annualPremium, totalPremium, totalDiscounts, totalSurcharges, totalTaxesFees
- **Payment**: paymentPlan, downPaymentRequired, downPaymentAmount, installmentFee
- **Flags**: isPaperless, isAutoPay, isPaidInFull, isRenewal
- **Prior Insurance**: priorPolicyNumber, priorInsuranceCarrier, priorInsuranceExpiration, priorInsuranceYears
- **Agency**: agentCode, agencyCode
- **Relationships**: Many-to-one with Customer, One-to-many with Coverage, Many-to-many with Vehicle
- **Timestamps**: createdAt, updatedAt

### 4. Coverage
Represents an individual insurance coverage or endorsement on a policy.

#### Key Fields:
- **Identification**: coverageType, coverageSubtype, coverageCode, coverageName
- **Limits**: limit1Amount, limit1Description, limit2Amount, limit2Description
- **Deductibles**: deductibleAmount, deductibleType
- **Premiums**: premium
- **Coverage Flags**: isRequired, isOptional, isDefaultSelected, isStateRequired, isPrimary, isExcess, isUmbrella
- **Coverage Types**: isRentalReimbursement, isRoadsideAssistance, isMedicalPayments, isPIP, isUMPD, isUMBI, isCustom
- **Relationships**: Many-to-one with Policy
- **Timestamps**: createdAt, updatedAt

### 5. Address (Embeddable)
Represents a physical address that can be embedded in other entities.

#### Key Fields:
- **Address Lines**: addressLine1, addressLine2, addressLine3
- **Location**: city, state, postalCode, countryCode, county, country
- **Metadata**: addressType, yearsAtAddress, monthsAtAddress, isPrimary

## Entity Relationships

```
Customer 1 --- * Vehicle
Customer 1 --- * Policy
Policy 1 --- * Coverage
Policy * --- * Vehicle
```

## Database Schema

### Tables
1. **customers**: Contains customer information with personal and insurance details
2. **vehicles**: Contains vehicle information with specifications and usage
3. **policies**: Contains policy information and terms
4. **coverages**: Contains individual coverage details
5. **policy_vehicles**: Junction table for many-to-many relationship between policies and vehicles

## Business Rules

### Premium Calculation
1. **Base Rates**:
   - Liability: $500 base
   - Collision: $300 base
   - Comprehensive: $200 base

2. **Rating Factors**:
   - Driver Age: Younger than 25 or older than 75 adds 20%
   - Vehicle Age: Newer than 3 years adds 15%
   - Vehicle Value: Over $50k adds 10%
   - Location: Urban areas add 10%
   - Credit Rating: Below 600 adds 15%
   - Prior Insurance: Lapse in coverage adds 10%
   - Accidents/Violations: Each incident adds 5-20% based on severity

3. **Discounts**:
   - Multi-car: 10%
   - Bundling (home + auto): 15%
   - Good Student: 10%
   - Defensive Driving Course: 5%
   - Anti-theft Device: 5%
   - Paperless Billing: 2%
   - Auto-pay: 2%
   - Paid in Full: 5%

## API Endpoints

### Customers
- `GET /api/customers` - List all customers
- `POST /api/customers` - Create a new customer
- `GET /api/customers/{id}` - Get customer by ID
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer
- `GET /api/customers/{id}/vehicles` - Get customer's vehicles
- `GET /api/customers/{id}/policies` - Get customer's policies

### Vehicles
- `GET /api/vehicles` - List all vehicles
- `POST /api/vehicles` - Create a new vehicle
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `PUT /api/vehicles/{id}` - Update vehicle
- `DELETE /api/vehicles/{id}` - Delete vehicle
- `GET /api/vehicles/vin/{vin}` - Get vehicle by VIN

### Policies
- `GET /api/policies` - List all policies
- `POST /api/policies` - Create a new policy
- `GET /api/policies/{id}` - Get policy by ID
- `PUT /api/policies/{id}` - Update policy
- `DELETE /api/policies/{id}` - Delete policy
- `POST /api/policies/{id}/bind` - Bind a quoted policy
- `POST /api/policies/{id}/cancel` - Cancel a policy
- `POST /api/policies/{id}/renew` - Renew a policy
- `GET /api/policies/{id}/coverages` - Get policy coverages
- `POST /api/policies/{id}/vehicles` - Add vehicle to policy
- `DELETE /api/policies/{policyId}/vehicles/{vehicleId}` - Remove vehicle from policy

### Coverages
- `GET /api/coverages` - List all coverages
- `POST /api/coverages` - Create a new coverage
- `GET /api/coverages/{id}` - Get coverage by ID
- `PUT /api/coverages/{id}` - Update coverage
- `DELETE /api/coverages/{id}` - Delete coverage

## Demo/Upgrade-Only Database Integration
- All domain models are annotated as JPA entities, and repositories are present.
- No datasource is configured by default; these are for demonstration and upgrade scenarios.
- The main quote logic can work with or without database persistence.
- Example fetch methods exist in the service layer but can be stubbed for demo purposes.

## Next Steps
1. Implement repository methods for all domain models
2. Create service layer with business logic for quote generation
3. Update controllers to handle CRUD operations for all entities
4. Add validation and error handling
5. Implement security and authentication
6. Add comprehensive logging and monitoring
7. Create API documentation with OpenAPI/Swagger