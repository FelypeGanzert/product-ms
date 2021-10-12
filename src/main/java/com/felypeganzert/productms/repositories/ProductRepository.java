package com.felypeganzert.productms.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.felypeganzert.productms.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("FROM Product p WHERE " +
            " ( " +
            "   (:name IS NOT NULL AND UPPER(p.name) LIKE UPPER(CONCAT('%',:name,'%')) ) " +
            "   OR (:description IS NOT NULL AND UPPER(p.description) LIKE UPPER(CONCAT('%',:description,'%')) ) " +
            "   or (:name IS NULL AND :description IS NULL )" +
            " ) " +
            " AND ( " +
            "   (:minPrice IS NOT NULL AND :maxPrice IS NOT NULL AND p.price between :minPrice and :maxPrice) " +
            "   OR (:minPrice IS NOT NULL AND :maxPrice IS NULL AND p.price >= :minPrice) " +
            "   OR (:minPrice IS NULL AND :maxPrice IS NOT NULL AND p.price <= :maxPrice) " +
            "   OR (:minPrice IS NULL AND :maxPrice IS NULL ) " +
            " ) ")
    List<Product> findByNameOrDescriptionAndPriceBetween(String name, String description, BigDecimal minPrice, BigDecimal maxPrice);

}
