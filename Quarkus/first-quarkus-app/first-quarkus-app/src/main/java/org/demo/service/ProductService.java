package org.demo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.demo.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    EntityManager em;

    public List<Product> getAllProducts() {
        return em.createNamedQuery("Product.findAll", Product.class)
                .getResultList();
    }

    public Product getProductById(Long id) {
        return em.createNamedQuery("Product.findById", Product.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public Product createProduct(Product product) {
        em.persist(product);        // persist stays the same
        return product;
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);   // reuse the named query
        if (product == null) {
            return null;
        }

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());

        return product;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        int deleted = em.createNamedQuery("Product.deleteById")
                .setParameter("id", id)
                .executeUpdate();
        return deleted > 0;
    }
}