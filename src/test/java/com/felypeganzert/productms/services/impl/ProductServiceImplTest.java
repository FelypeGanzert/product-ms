package com.felypeganzert.productms.services.impl;

import static com.felypeganzert.productms.utils.AppConstantes.ID;
import static com.felypeganzert.productms.utils.AppConstantes.PRODUCT;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.exceptions.RecursoNaoEncontradoException;
import com.felypeganzert.productms.mappers.impl.ProductMapperImpl;
import com.felypeganzert.productms.repositories.ProductRepository;
import com.felypeganzert.productms.utils.SearchParam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapperImpl mapper;

    @Mock
    private Pageable pageableMock;

    @BeforeEach
    void setUp() {
        Mockito.when(mapper.toProductDTO(ArgumentMatchers.any())).thenReturn(criarProductDTOValido());
        Mockito.when(mapper.toProduct(ArgumentMatchers.any())).thenReturn(criarProductValido());
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(criarOptionalProductValido());
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoInserir() {
        service.insert(criarProductDTOValido());

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoAtualizar() {
        service.update(1L, criarProductDTOValido());

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucessoAoDeletar() {
        service.delete(1L);

        Mockito.verify(repository, times(1)).delete(ArgumentMatchers.any(Product.class));
    }

    @Test
    void deveChamarFindByIdDoReposirotyComSucesso() {
        Long id = 1L;
        service.findById(id);

        Mockito.verify(repository, times(1)).findById(id);
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Long id = 1L;

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(PRODUCT, ID, id);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class)
                .isThrownBy(() -> service.findById(id)).withMessage(exception.getMessage());
    }

    @Test
    void deveChamarFindAllDoRepositoryComSucessoAoBuscarTudo() {
        List<Product> products= Arrays.asList(new Product(), new Product());
        Mockito.when(repository.findAll()).thenReturn(products);

        service.findAll();
        
        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void deveChamarFindByParamsDoRepositoryComSucesso() {
        SearchParam searchParam = new SearchParam();
        
        service.findAllWithParameters(searchParam);

        Mockito.verify(repository, times(1)).findByParams(ArgumentMatchers.any(SearchParam.class));
    }

    @Test
    void deveChamarFindAllDoRepositoryComSucessoAoBucasPaginado() {
        PageRequest paginacao = PageRequest.of(1, 10);
        List<Product> products= Arrays.asList(new Product(), new Product());
        Page<Product> productsPaged = new PageImpl<>(products, paginacao, products.size());
        Mockito.when(repository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(productsPaged);
        
        service.findAllPaged(PageRequest.of(0, 100));

        Mockito.verify(repository, times(1)).findAll(ArgumentMatchers.any(PageRequest.class));
    }

    private ProductDTO criarProductDTOValido() {
        return ProductDTO.builder().name("Name").description("Description").price(BigDecimal.valueOf(1.0)).build();
    }

    private Product criarProductValido() {
        return Product.builder().name("Name").description("Description").price(BigDecimal.valueOf(1.0)).build();
    }

    private Optional<Product> criarOptionalProductValido() {
        return Optional.of(criarProductValido());
    }

}
