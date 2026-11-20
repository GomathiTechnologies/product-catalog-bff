package com.example.productcatalogbff;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class ProductControllerTest {
    @Test void fetchesProductsFromCatalogService() throws Exception {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("http://catalog:8081/api/products"))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withSuccess("""
                [{"id":"internet-500","name":"Internet 500","description":"High-speed home internet",
                  "downloadSpeedMbps":500,"monthlyPrice":59.99,"currency":"CAD"}]
                """, MediaType.APPLICATION_JSON));
        var mvc = MockMvcBuilders.standaloneSetup(new ProductController(
                new ProductService(new ProductCatalogClient(builder, "http://catalog:8081")))).build();
        mvc.perform(get("/api/products")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("internet-500"))
            .andExpect(jsonPath("$[0].monthlyPrice").value(59.99));
        server.verify();
    }
}
