package com.felypeganzert.productms.services;

import java.util.List;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.utils.SearchParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductDTO insert(ProductDTO productDTO);

    ProductDTO update(Long id, ProductDTO productDTO);

    void delete(Long id);

    ProductDTO findById(Long id);

    List<ProductDTO> findAll();
    
    List<ProductDTO> findAllWithParameters(SearchParam searchParam);

    Page<ProductDTO> findAllPaged(PageRequest pageRequest);

}
