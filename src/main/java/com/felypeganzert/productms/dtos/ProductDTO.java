package com.felypeganzert.productms.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name é obrigatório")
    private String name;

    @NotBlank(message = "Description é obrigatório")
    private String description;

    @NotNull(message = "Preço deve ser positivo")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal price;
}
