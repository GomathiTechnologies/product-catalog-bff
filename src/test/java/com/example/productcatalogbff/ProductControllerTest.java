package com.example.productcatalogbff;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ProductController()).build();

    @Test
    void returnsSampleInternetProduct() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0].id").value("internet-500"))
                .andExpect(jsonPath("$[0].downloadSpeedMbps").value(500))
                .andExpect(jsonPath("$[0].monthlyPrice").value(59.99))
                .andExpect(jsonPath("$[0].currency").value("CAD"));
    }
}
