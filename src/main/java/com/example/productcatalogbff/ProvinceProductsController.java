package com.example.productcatalogbff;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProvinceProductsController {
    private final ProductCatalogClient client;

    public ProvinceProductsController(ProductCatalogClient client) {
        this.client = client;
    }

    @GetMapping("/dynamic/province/products")
    public JsonNode products(@RequestParam String province) {
        try {
            return client.getProvinceProducts(province);
        } catch (RestClientResponseException exception) {
            throw new ResponseStatusException(exception.getStatusCode(), "Province products request failed", exception);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Product service unavailable", exception);
        }
    }
}
