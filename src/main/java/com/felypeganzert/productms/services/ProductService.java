package com.felypeganzert.productms.services;

import com.felypeganzert.productms.dtos.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductDTO insert(ProductDTO product);

    ProductDTO update(Long id, ProductDTO product);

    void delete(Long id);

    ProductDTO findById(Long id);

    Page<ProductDTO> findAllPaged(PageRequest pageRequest);

}