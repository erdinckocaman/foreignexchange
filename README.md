# Foreign Exchange Service

## Project Overview

This is a Spring Boot microservice application that calculates exchange rates and performs currency conversions. The service provides functionality for exchange rate calculations, amount conversions, and querying conversion history.

## Features

- Calculate current exchange rates between two currencies
- Convert specific amounts from one currency to another
- Perform bulk currency conversions (in CSV format)
- Query and filter historical conversion transactions
- Error handling and coding system

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/exchange-rates` | GET | Calculates the exchange rate between two currencies |
| `/exchange-rates/currency-conversion` | POST | Converts a specific amount from one currency to another |
| `/exchange-rates/bulk-currency-conversion` | POST | Performs bulk currency conversions via CSV file |
| `/exchange-rates/currency-conversion-history` | GET | Queries historical conversion transactions |

## Technical Architecture

- Developed using **Onion Architecture** principles
- REST APIs implemented with **Spring Boot** web framework
- Database operations abstracted using Repository pattern
- Uses CurrencyLayer as an external service provider

## Error Codes

| Code | Description |
|------|-------------|
| FE_001 | Invalid currency code |
| FE_002 | Invalid amount |
| FE_101 | CurrencyLayer service failure |
| FE_201 | Invalid user input |

## Development Environment

- Java
- Spring Boot
- Maven

## Setup and Run

```bash
# Clone the project
git clone https://github.com/erdinckocaman/foreignexchange.git

# Navigate to project directory
cd foreignexchange

# Build with Maven
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

## Usage Examples

### Calculate exchange rate:
```
GET /exchange-rates?base=USD&target=EUR
```

### Convert currency:
```
POST /exchange-rates/currency-conversion
Content-Type: application/json

{
  "amount": 100,
  "baseCurrency": "USD",
  "targetCurrency": "EUR"
}
```

### Query conversion history:
```
GET /exchange-rates/currency-conversion-history?transactionDate=2023-01-01&page=1&size=10
```