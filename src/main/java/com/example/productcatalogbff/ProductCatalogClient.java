package com.example.productcatalogbff;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class ProductCatalogClient {
    private final RestClient restClient;
    public ProductCatalogClient(RestClient.Builder builder, @Value("${product-catalog.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }
    public JsonNode chat(JsonNode request) {
        return restClient.post().uri("/ai/chat").contentType(MediaType.APPLICATION_JSON)
                .body(request).retrieve().body(JsonNode.class);
    }
    public List<Product> getProducts() {
        return restClient.get().uri("/api/products").retrieve().body(new ParameterizedTypeReference<List<Product>>() {});
    }
}
