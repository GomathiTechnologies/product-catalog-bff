package com.example.productcatalogbff;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class KnowledgeChatControllerTest {
    @Test void forwardsRequestAndResponse() throws Exception {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://catalog:8081/ai/knowledge/chat")).andExpect(method(HttpMethod.POST))
                .andExpect(content().json("{\"prompt\":\"IP?\"}"))
                .andRespond(withSuccess("{\"response\":\"Yes\",\"sources\":[{\"fileName\":\"ip.pdf\"}]}", MediaType.APPLICATION_JSON));
        var mvc = MockMvcBuilders.standaloneSetup(new KnowledgeChatController(new ProductCatalogClient(builder, "http://catalog:8081"))).build();
        mvc.perform(post("/ai/knowledge/chat").contentType(MediaType.APPLICATION_JSON).content("{\"prompt\":\"IP?\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.sources[0].fileName").value("ip.pdf"));
        server.verify();
    }

    @Test void forwardsUpstreamError() throws Exception {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://catalog:8081/ai/knowledge/chat"))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON).body("{\"error\":\"Knowledge base is empty\"}"));
        var mvc = MockMvcBuilders.standaloneSetup(new KnowledgeChatController(new ProductCatalogClient(builder, "http://catalog:8081"))).build();
        mvc.perform(post("/ai/knowledge/chat").contentType(MediaType.APPLICATION_JSON).content("{\"prompt\":\"IP?\"}"))
                .andExpect(status().isServiceUnavailable()).andExpect(jsonPath("$.error").value("Knowledge base is empty"));
        server.verify();
    }
}
