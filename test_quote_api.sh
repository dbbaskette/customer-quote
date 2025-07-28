#!/bin/bash
# test_quote_api.sh
# Test the /quote endpoint with sample data

API_URL="http://localhost:8080/quote"

cat <<EOF > sample_quote_request.json
{
  "customerId": "cust123",
  "customerName": "Jane Doe",
  "customerAge": 28,
  "vehicleId": "veh456",
  "vehicleYear": 2018,
  "vehicleMake": "Toyota"
}
EOF

echo "Sending request to $API_URL..."
curl -X POST -H "Content-Type: application/json" -d @sample_quote_request.json "$API_URL" | jq 