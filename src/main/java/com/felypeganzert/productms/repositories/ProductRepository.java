package com.felypeganzert.productms.repositories;

import com.felypeganzert.productms.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
