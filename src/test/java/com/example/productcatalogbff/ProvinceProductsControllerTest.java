package com.example.productcatalogbff;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ProvinceProductsControllerTest {
    @Test void forwardsProvinceAndReturnsProducts() throws Exception {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://catalog:8081/dynamic/province/products?province=ON"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("{\"province\":\"Ontario\",\"message\":\"Plans available\",\"products\":[{\"id\":\"on-super\"}]}", MediaType.APPLICATION_JSON));
        var mvc = MockMvcBuilders.standaloneSetup(new ProvinceProductsController(new ProductCatalogClient(builder, "http://catalog:8081"))).build();
        mvc.perform(get("/dynamic/province/products").param("province", "ON"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.products[0].id").value("on-super"));
        server.verify();
    }
}
