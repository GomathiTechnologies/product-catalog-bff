package com.example.productcatalogbff;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@RestController
public class KnowledgeChatController {
    private final ProductCatalogClient client;

    public KnowledgeChatController(ProductCatalogClient client) { this.client = client; }

    @PostMapping("/ai/knowledge/chat")
    public ResponseEntity<?> chat(@RequestBody JsonNode request) {
        try {
            return ResponseEntity.ok(client.knowledgeChat(request));
        } catch (RestClientResponseException exception) {
            return ResponseEntity.status(exception.getStatusCode()).contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getResponseBodyAsString());
        } catch (RestClientException exception) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("error", "Knowledge chat service unavailable."));
        }
    }
}
