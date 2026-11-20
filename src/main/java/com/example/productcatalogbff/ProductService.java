package com.example.productcatalogbff;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
    private final ProductCatalogClient client;
    public ProductService(ProductCatalogClient client) { this.client = client; }
    public List<Product> getProducts() { return client.getProducts(); }
}
