package com.felypeganzert.productms.mappers;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product;

public interface ProductMapper {
    
    Product toProduct(ProductDTO dto);
    
    ProductDTO toProductDTO(Product product);

}
