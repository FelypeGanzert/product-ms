package com.felypeganzert.productms.services.impl;

import static com.felypeganzert.productms.utils.AppConstantes.ID;
import static com.felypeganzert.productms.utils.AppConstantes.PRODUCT;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.exceptions.RecursoNaoEncontradoException;
import com.felypeganzert.productms.mappers.ProductMapper;
import com.felypeganzert.productms.repositories.ProductRepository;
import com.felypeganzert.productms.services.ProductService;
import com.felypeganzert.productms.utils.SearchParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional
    @Override
    public ProductDTO insert(ProductDTO productDTO) {
        productDTO.setId(0L); // garante que será gerado um novo id
        Product product = mapper.toProduct(productDTO);

        product = repository.save(product);

        return mapper.toProductDTO(product);
    }

    @Transactional
    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = findProductById(id);
        mapper.parseValuesToProduct(productDTO, product);

        product = repository.save(product);

        return mapper.toProductDTO(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Product product = findProductById(id);
        repository.delete(product);
    }

    @Override
    public ProductDTO findById(Long id) {
        return mapper.toProductDTO(findProductById(id));
    }

    private Product findProductById(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(PRODUCT, ID, id));
    }

    @Override
    public List<ProductDTO> findAll(){
        List<Product> list = repository.findAll();
        return list.stream().map(p -> mapper.toProductDTO(p)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findAllWithParameters(SearchParam searchParam){
        List<Product> list = repository.findByParams(searchParam);
        return list.stream().map(p -> mapper.toProductDTO(p)).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(p -> mapper.toProductDTO(p)); 
    }
    
}
