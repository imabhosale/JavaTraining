package org.demo.service;

import org.demo.model.Product;
import org.demo.repository.ProductRepository;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ProductService {

    @Inject
    Logger log;

    @Inject
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id);
        if (product == null) return null;

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());

        return product;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
