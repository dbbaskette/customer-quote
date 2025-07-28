# quick_reference.md

## Example: Quote Request (POST /quote)

Request JSON:
```
{
  "customerId": "cust123",
  "customerName": "Jane Doe",
  "customerAge": 28,
  "vehicleId": "veh456",
  "vehicleYear": 2018,
  "vehicleMake": "Toyota"
}
```

Response JSON:
```
{
  "quoteId": "...",
  "customer": {
    "id": "cust123",
    "name": "Jane Doe",
    "age": 28
  },
  "vehicle": {
    "id": "veh456",
    "year": 2018,
    "make": "Toyota"
  },
  "coverages": {
    "liability": 500,
    "collision": 320,
    "comprehensive": 230
  },
  "expirationDate": "2024-07-30T12:00:00.000+00:00"
}
``` 