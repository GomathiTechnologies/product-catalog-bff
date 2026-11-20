package com.example.productcatalogbff;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AiChatControllerTest {
    @Test void forwardsPromptAndReturnsServiceResponse() throws Exception {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://catalog:8081/ai/chat"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().json("{\"prompt\":\"Hello\"}"))
            .andRespond(withSuccess("{\"response\":\"Sample response to: Hello\"}", MediaType.APPLICATION_JSON));
        var mvc = MockMvcBuilders.standaloneSetup(new AiChatController(new ProductCatalogClient(builder, "http://catalog:8081"))).build();
        mvc.perform(post("/ai/chat").contentType(MediaType.APPLICATION_JSON).content("{\"prompt\":\"Hello\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.response").value("Sample response to: Hello"));
        server.verify();
    }
}
