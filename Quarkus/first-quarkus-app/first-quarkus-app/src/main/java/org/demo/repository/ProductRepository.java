package org.demo.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.demo.model.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    // Custom finder example
    public Product findByName(String name) {
        return find("name", name).firstResult();
    }
}
