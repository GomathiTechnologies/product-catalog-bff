# Product Catalog BFF

A Spring Boot backend for frontend that retrieves products from `product-catalog-service`. Requires Java 17 or later and Maven. Start the catalog service on port 8081 first (see its README).

```sh
mvn spring-boot:run
```

Request the catalog at `GET http://localhost:8080/api/products`:

```json
[
  {
    "id": "internet-500",
    "name": "Internet 500",
    "description": "High-speed home internet",
    "downloadSpeedMbps": 500,
    "monthlyPrice": 59.99,
    "currency": "CAD"
  }
]
```

Set `PRODUCT_CATALOG_BASE_URL` to override the catalog service URL (default `http://localhost:8081`). Run the API test with `mvn test`. The catalog service supplies the sample data.

The BFF also forwards `POST /ai/chat` with a JSON body such as `{"prompt":"Hello"}` to the catalog service and returns its response.
