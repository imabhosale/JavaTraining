package com.demo.FirstProject.Repo;

import com.demo.FirstProject.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
