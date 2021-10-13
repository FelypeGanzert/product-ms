package com.felypeganzert.productms.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.utils.SearchParam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductCustomRepositoryImplTest {

    @Autowired
    private ProductCustomRepositoryImpl repository;

    @Autowired
    private ProductRepository repositoryGeral;

    private final List<Product> products = List.of(new Product(0L, "caneta", "azul", BigDecimal.valueOf(20)),
            new Product(0L, "guarda-chuva", "o mais grande do universo", BigDecimal.valueOf(75)),
            new Product(0L, "blusa", "cor rosa", BigDecimal.valueOf(50)),
            new Product(0L, "vaso com rosas", "muitas rosas", BigDecimal.valueOf(90)),
            new Product(0L, "chinelo", "na cor azul com detalhes em rosa", BigDecimal.valueOf(150)),
            new Product(0L, "camisa rosa", "com personagens de animes", BigDecimal.valueOf(60)),
            new Product(0L, "jaqueta", "alguns chamam de blusa", BigDecimal.valueOf(10)));

    @BeforeEach
    void destruir() {
        repositoryGeral.deleteAll();
    }

    private void verificarSeQtdEsperadaEhValida(Long qtdEsperada) {
        assertThat(qtdEsperada)
            .withFailMessage("É necessário que seja esperado no mínio um valor")
            .isGreaterThanOrEqualTo(1);

        assertThat(qtdEsperada)
            .withFailMessage("Não pode ser esperado o tamanho da lista toda, pois assim não há como saber se os filtros funcionaram")
            .isLessThan(products.size());
    }

    @Test
    @DisplayName("Deve trazer produtos contendo texto no nome ou na descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescription() {
        repositoryGeral.saveAll(products);

        SearchParam searchParam = SearchParam.builder().q("ROSA").build();
        Long qtdEsperada = products.stream().filter(
                    p -> containsIgnoreCase(p.getName(), searchParam.getQ())
                        || containsIgnoreCase(p.getDescription(), searchParam.getQ())
                ).count();

        List<Product> list = repository.findByParams(searchParam);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos acima de determinado preço")
    void deveBuscarProdutosComValorMinimo() {
        repositoryGeral.saveAll(products);

        SearchParam searchParam = SearchParam.builder().minPrice(BigDecimal.valueOf(50)).build();
        Long qtdEsperada = products.stream().filter(p -> p.getPrice().compareTo(searchParam.getMinPrice()) >= 0).count();

        List<Product> list = repository.findByParams(searchParam);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos abaixo de determinado preço")
    void deveBuscarProdutosComValorMaximo() {
        repositoryGeral.saveAll(products);

        SearchParam searchParam = SearchParam.builder().maxPrice(BigDecimal.valueOf(50)).build();
        Long qtdEsperada = products.stream().filter(p -> p.getPrice().compareTo(searchParam.getMaxPrice()) <= 0).count();

        List<Product> list = repository.findByParams(searchParam);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos entre determinados valores")
    void deveBuscarProdutosComValorEntre() {
        repositoryGeral.saveAll(products);

        SearchParam searchParam = SearchParam.builder()
                                            .minPrice(BigDecimal.valueOf(50))
                                            .maxPrice(BigDecimal.valueOf(100)).build();
        Long qtdEsperada = products.stream()
                .filter(p -> p.getPrice().compareTo(searchParam.getMinPrice()) >= 0
                                && p.getPrice().compareTo(searchParam.getMaxPrice()) <= 0
                ).count();

        List<Product> list = repository.findByParams(searchParam);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos entre determinados valores com texto no nome ou descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescriptionEValorEntre() {
        repositoryGeral.saveAll(products);

        SearchParam searchParam = SearchParam.builder()
                                            .q("ROSA")
                                            .minPrice(BigDecimal.valueOf(60))
                                            .maxPrice(BigDecimal.valueOf(100)).build();
        Long quantidadeEsperada = products.stream()
                .filter(p -> 
                    (containsIgnoreCase(p.getName(), searchParam.getQ())
                        || containsIgnoreCase(p.getDescription(),  searchParam.getQ()))
                    && (p.getPrice().compareTo(searchParam.getMinPrice()) >= 0 && p.getPrice().compareTo(searchParam.getMaxPrice()) <= 0)
                ).count();

        List<Product> list = repository.findByParams(searchParam);

        verificarSeQtdEsperadaEhValida(quantidadeEsperada);
        assertThat(list).hasSize(quantidadeEsperada.intValue());
    }

    private boolean containsIgnoreCase(String t1, String t2) {
        return t1.toUpperCase().contains(t2.toUpperCase());
    }

}
