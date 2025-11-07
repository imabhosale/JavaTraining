package org.demo;

import jakarta.persistence.EntityManager;
import org.demo.model.Product;
import org.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    EntityManager em;

    @InjectMocks
    ProductService productService;

    @Test
    void testCreateProduct() {

        Product product = new Product("Laptop", "Gaming laptop", new BigDecimal("85000"), 10);

        doNothing().when(em).persist(any(Product.class));

        // Act
        Product createdProduct = productService.createProduct(product);

        // Assert
        assertEquals("Laptop", createdProduct.getName());
        assertEquals(10, createdProduct.getQuantity());

        // Verify persist() called once
        verify(em, times(1)).persist(any(Product.class));
    }
}
