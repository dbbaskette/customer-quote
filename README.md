# Insurance Quote System

A comprehensive insurance quote generation system built with Spring Boot and Java. This application provides a robust backend for managing customers, vehicles, policies, and generating insurance quotes with various rating factors.

## Features

- **Customer Management**: Create, read, update, and delete customer information
- **Vehicle Management**: Track vehicles with detailed attributes
- **Policy Management**: Manage insurance policies with different coverage options
- **Quote Generation**: Generate insurance quotes based on various rating factors
- **Soft Delete**: Support for soft deletion of entities
- **RESTful API**: Comprehensive API endpoints for all operations

## Technology Stack

- **Backend**: Spring Boot 2.7.x
- **Database**: H2 (in-memory, for development)
- **Build Tool**: Maven
- **Java**: 11+

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/customer-quote.git
   cd customer-quote
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## API Documentation

Once the application is running, you can access the following:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

## Domain Model

The system includes the following main entities:

- **Customer**: Represents an insurance customer with personal and driving information
- **Vehicle**: Represents a vehicle that can be insured
- **Policy**: Represents an insurance policy with coverages
- **Coverage**: Represents different types of insurance coverage

## Testing

Run the test suite with:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
