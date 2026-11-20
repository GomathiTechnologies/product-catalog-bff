package com.example.productcatalogbff;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @GetMapping
    public List<Product> getProducts() {
        return List.of(new Product("internet-500", "Internet 500", "High-speed home internet",
                500, new BigDecimal("59.99"), "CAD"));
    }

    public record Product(String id, String name, String description, int downloadSpeedMbps,
                          BigDecimal monthlyPrice, String currency) {}
}
