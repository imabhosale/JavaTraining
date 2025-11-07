package org.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.demo.util.EncryptDecryptConverter;

import java.math.BigDecimal;

@Entity

@Getter
@Setter
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "Product.findAll",
                query = "SELECT p FROM Product p"
        ),
        @NamedQuery(
                name = "Product.findById",
                query = "SELECT p FROM Product p WHERE p.id = :id"
        ),
        @NamedQuery(
                name = "Product.deleteById",
                query = "DELETE FROM Product p WHERE p.id = :id"
        )
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = EncryptDecryptConverter.class)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private int quantity;

    // Constructors
    public Product() {}

    public Product(String name, String description, BigDecimal price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
