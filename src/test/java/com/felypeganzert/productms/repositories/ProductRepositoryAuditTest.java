package com.felypeganzert.productms.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.felypeganzert.productms.entities.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryAuditTest {

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    @Test
    void deveAtribuirCreatedDateELastModifiedDateAoSalvarUmNovoProdutoValido(){
        Product p = Product.builder().description("description").name("name").price(BigDecimal.valueOf(1)).build();

        p = repository.save(p);

        assertThat(p.getCreatedDate()).isNotNull();
        assertThat(p.getLastModifiedDate()).isNotNull();
    }

    @Test
    void deveAtualizarSomenteLastModifiedDateAoAtualizarUmProdutoExistente(){
        Product p = Product.builder().description("description").name("name").price(BigDecimal.valueOf(1)).build();
        p = repository.save(p);
        LocalDateTime createdDate = p.getCreatedDate();
        LocalDateTime modifiedDate = p.getLastModifiedDate();

        String newDescription = "new description";
        p.setDescription(newDescription);
        p = repository.save(p);
        
        assertThat( p.getDescription()).isEqualTo(newDescription);
        assertThat(p.getCreatedDate()).isEqualToIgnoringNanos(createdDate);
        assertThat(p.getLastModifiedDate()).isAfter(modifiedDate);
    }

}
