package com.felypeganzert.productms.resources;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product_;
import com.felypeganzert.productms.services.ProductService;
import com.felypeganzert.productms.utils.SearchParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Api("Product MS")
public class ProductResource {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Criação de um produto")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Produto criado")
    })
    public ProductDTO insert(@RequestBody @Valid ProductDTO productDTO){
        return service.insert(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualização de um produto")
    public ProductDTO update(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO){
        return service.update(id, productDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Busca de um produto por ID")
    public ProductDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lista de produtos")
    public List<ProductDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lista de produtos filtrados")
    public List<ProductDTO> findAllWithParameters(
        final @RequestParam(value = "q", required = false) String q,
        final @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
        final @RequestParam(value = "max_price", required = false) BigDecimal maxPrice
    ){
        return service.findAllWithParameters(new SearchParam(q, minPrice, maxPrice));
    }

    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lista de produtos paginados")
    public Page<ProductDTO> findAllPaged(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, Product_.NAME);

        return service.findAllPaged(pageRequest);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deleção de um produto")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Produto deletado")
    })
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
    
}
