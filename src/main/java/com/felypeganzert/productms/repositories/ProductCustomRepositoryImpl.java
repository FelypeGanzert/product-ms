package com.felypeganzert.productms.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.felypeganzert.productms.entities.Product;
import com.felypeganzert.productms.entities.Product_;
import com.felypeganzert.productms.utils.SearchParam;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Product> findByParams(SearchParam searchParam) {
        
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = builder.createQuery(Product.class);
        Root<Product> p = cq.from(Product.class);

        if(searchParam.getQ() == null){
            searchParam.setQ("");
        }
        
        Predicate nameOrDescriptionContainsText = builder.or(
            builder.like(
                builder.lower(p.get(Product_.NAME)),
                "%" + searchParam.getQ().toLowerCase() + "%"
            ),
            builder.like(
                builder.lower(p.get(Product_.DESCRIPTION)),
                "%" + searchParam.getQ().toLowerCase() + "%"
            )
        );

        Predicate priceGreaterThanOrEqualTo = builder.greaterThanOrEqualTo(p.get(Product_.PRICE), searchParam.getMinPrice());
        Predicate priceLessThanOrEqualTo = builder.lessThanOrEqualTo(p.get(Product_.PRICE), searchParam.getMaxPrice());

        Map<Predicate, Boolean> filters = Map.of(
            nameOrDescriptionContainsText, !searchParam.getQ().isBlank(),
            priceGreaterThanOrEqualTo, consideraBigDecimal(searchParam.getMinPrice()),
            priceLessThanOrEqualTo, consideraBigDecimal(searchParam.getMaxPrice())
        );

        Predicate[] valids = filters.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toArray(Predicate[]::new);

        return entityManager
                    .createQuery(cq.where(valids))
                    .getResultList();
    }

    private boolean consideraBigDecimal(BigDecimal value){
        return value != null && value.compareTo(BigDecimal.ZERO) != 0;
    }
    
}
