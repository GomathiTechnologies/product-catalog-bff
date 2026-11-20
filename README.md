# Product Catalog BFF

A small Spring Boot service returning a sample home internet product. Requires Java 17 or later and Maven.

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

Run the API test with `mvn test`. The product is sample data held in memory.
