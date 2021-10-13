package com.felypeganzert.productms.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SearchParam {

    private String q;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
}
