package com.felypeganzert.productms.repositories;

import java.util.List;

import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.utils.SearchParam;

public interface ProductCustomRepository {

    List<Product> findByParams(SearchParam searchParam);

}
