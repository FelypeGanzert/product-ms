package com.felypeganzert.productms.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.felypeganzert.productms.entities.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private final List<Product> products = List.of(new Product(0L, "caneta", "azul", BigDecimal.valueOf(20)),
            new Product(0L, "guarda-chuva", "o mais grande do universo", BigDecimal.valueOf(75)),
            new Product(0L, "blusa", "cor rosa", BigDecimal.valueOf(50)),
            new Product(0L, "vaso com rosas", "muitas rosas", BigDecimal.valueOf(90)),
            new Product(0L, "chinelo", "na cor azul com detalhes em rosa", BigDecimal.valueOf(150)),
            new Product(0L, "camisa rosa", "com personagens de animes", BigDecimal.valueOf(60)),
            new Product(0L, "jaqueta", "alguns chamam de blusa", BigDecimal.valueOf(10)));

    @BeforeEach
    void destruir() {
        repository.deleteAll();
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
    @DisplayName("Deve trazer produtos contendo texto no nome")
    void deveBuscarProdutosContendoTextoEmName() {
        repository.saveAll(products);

        String name = "ROSA";
        String description = null;
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        Long qtdEsperada = products.stream().filter(p -> containsIgnoreCase(p.getName(), name)).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos contendo texto na descrição")
    void deveBuscarProdutosContendoTextoEmDescription() {
        repository.saveAll(products);

        String name = null;
        String description = "ROSA";
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        Long qtdEsperada = products.stream().filter(p -> containsIgnoreCase(p.getDescription(), description)).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos contendo texto no nome ou na descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescription() {
        repository.saveAll(products);

        String name = "CHUVA";
        String description = "ANIME";
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        Long qtdEsperada = products.stream().filter(
                    p -> containsIgnoreCase(p.getName(), name) || containsIgnoreCase(p.getDescription(), description))
                .count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos acima de determinado preço")
    void deveBuscarProdutosComValorMinimo() {
        repository.saveAll(products);

        String name = null;
        String description = null;
        BigDecimal minPrice = BigDecimal.valueOf(50);
        BigDecimal maxPrice = null;
        Long qtdEsperada = products.stream().filter(p -> p.getPrice().compareTo(minPrice) >= 0).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos abaixo de determinado preço")
    void deveBuscarProdutosComValorMaximo() {
        repository.saveAll(products);

        String name = null;
        String description = null;
        BigDecimal minPrice = null;
        BigDecimal maxPrice = BigDecimal.valueOf(50);
        Long qtdEsperada = products.stream().filter(p -> p.getPrice().compareTo(maxPrice) <= 0).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos entre determinados valores")
    void deveBuscarProdutosComValorEntre() {
        repository.saveAll(products);

        String name = null;
        String description = null;
        BigDecimal minPrice = BigDecimal.valueOf(50);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        Long qtdEsperada = products.stream()
                .filter(p -> p.getPrice().compareTo(minPrice) >= 0 && p.getPrice().compareTo(maxPrice) <= 0).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(qtdEsperada);
        assertThat(list).hasSize(qtdEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos acima de determinado valor com texto no nome ou descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescriptionEValorMinimo() {
        repository.saveAll(products);

        String name = "ROSA";
        String description = "ROSA";
        BigDecimal minPrice = BigDecimal.valueOf(70);
        BigDecimal maxPrice = null;

        Long quantidadeEsperada = products.stream()
                .filter(p ->
                    (containsIgnoreCase(p.getName(), name) || containsIgnoreCase(p.getDescription(), description))
                    && p.getPrice().compareTo(minPrice) >= 0
                ).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(quantidadeEsperada);
        assertThat(list).hasSize(quantidadeEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos abaixo de determinado valor com texto no nome ou descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescriptionEValorMaximo() {
        repository.saveAll(products);

        String name = "BLUSA";
        String description = "BLUSA";
        BigDecimal minPrice = null;
        BigDecimal maxPrice = BigDecimal.valueOf(30);

        Long quantidadeEsperada = products.stream()
                .filter(p ->
                    (containsIgnoreCase(p.getName(), name) || containsIgnoreCase(p.getDescription(), description))
                        && p.getPrice().compareTo(maxPrice) <= 0
                ).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(quantidadeEsperada);
        assertThat(list).hasSize(quantidadeEsperada.intValue());
    }

    @Test
    @DisplayName("Deve trazer produtos entre determinados valores com texto no nome ou descrição")
    void deveBuscarProdutosContendoTextoEmNameOuEmDescriptionEValorEntre() {
        repository.saveAll(products);

        String name = "ROSA";
        String description = "ROSA";
        BigDecimal minPrice = BigDecimal.valueOf(60);
        BigDecimal maxPrice = BigDecimal.valueOf(100);

        Long quantidadeEsperada = products.stream()
                .filter(p -> 
                    (containsIgnoreCase(p.getName(), name) || containsIgnoreCase(p.getDescription(), description))
                        && (p.getPrice().compareTo(minPrice) >= 0 && p.getPrice().compareTo(maxPrice) <= 0)
                ).count();

        List<Product> list = repository.findByNameOrDescriptionAndPriceBetween(name, description, minPrice, maxPrice);

        verificarSeQtdEsperadaEhValida(quantidadeEsperada);
        assertThat(list).hasSize(quantidadeEsperada.intValue());
    }

    private boolean containsIgnoreCase(String t1, String t2) {
        return t1.toUpperCase().contains(t2.toUpperCase());
    }

}
