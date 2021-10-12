package com.felypeganzert.productms.mappers.impl;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.mappers.ProductMapper;

public class ProductMapperImpl implements ProductMapper{

    @Override
    public Product toProduct(ProductDTO dto) {
        return Product.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .price(dto.getPrice())
                    .build();
    }

    @Override
    public ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
    }

    @Override
    public void parseValuesToProduct(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
    }
    
}
