package com.example.productcatalogbff;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AiChatController {
    private final ProductCatalogClient client;

    public AiChatController(ProductCatalogClient client) {
        this.client = client;
    }

    @PostMapping("/ai/chat")
    public JsonNode chat(@RequestBody JsonNode request) {
        try {
            return client.chat(request);
        } catch (RestClientResponseException exception) {
            throw new ResponseStatusException(exception.getStatusCode(), "Chat request failed", exception);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Chat service unavailable", exception);
        }
    }
}
