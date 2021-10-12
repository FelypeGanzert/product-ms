package com.felypeganzert.productms.mappers;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product;

import org.springframework.stereotype.Component;

@Component
public interface ProductMapper {
    
    Product toProduct(ProductDTO dto);
    
    ProductDTO toProductDTO(Product product);

    void parseValuesToProduct(ProductDTO dto, Product product);

}
