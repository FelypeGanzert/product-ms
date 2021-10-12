package com.felypeganzert.productms.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    // q => name or description
    // min_price
    // max_price 

    // deve trazer produtos contendo texto no nome ou na descrição, q = aaaa
    // deve trazer produtos acima de determinado preço, min_price = 50
    // deve trazer produtos abaixo de determinado preço, max_price = 100
    // deve trazer produtos entre determinados valores, min_price = 50, max_price = 100
    // deve trazer produtos acima de determinado valor com texto no nome ou descrição, min_price = 50, q = aaa
    // deve trazer produtos abaixo de determinado valor com texto no nome ou descrição,  max_price = 50, q = aaa
    // deve trazer produtos entre determinados valores com texto no nome ou descrição, min_price = 50, max_price = 50, q = aaa
    
}
