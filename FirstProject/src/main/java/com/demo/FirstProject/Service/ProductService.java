package com.demo.FirstProject.Service;


import com.demo.FirstProject.Exceptions.ProductNotFoundException;
import com.demo.FirstProject.Model.Product;
import com.demo.FirstProject.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepo productRepo;

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public Product getProductById(int id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }


    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public void deleteProduct(int id) {
        Product pro=productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        productRepo.deleteById(pro.getId());
    }

}
