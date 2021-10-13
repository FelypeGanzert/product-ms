package com.felypeganzert.productms.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.productms.exceptions.ApiError;

import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseBodyMatchers {
    
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher contemObjetoComoJson(Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(expectedObject);
        };
    }

    public ResultMatcher contemErro(String erroEsperado) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            ApiError apiError = objectMapper.readValue(json, ApiError.class);

            List<String> errosGerados = Arrays.asList(apiError.getMessage().split("; "));
            List<String> errosEsperados = Arrays.asList(erroEsperado.split("; "));
            List<String> errosNaoEncontrados = new ArrayList<String>();

            errosEsperados.forEach(e -> {
                if(!errosGerados.contains(e)){
                    errosNaoEncontrados.add(e);
                }
            });

            String naoEncontrados  = errosEsperados.stream().collect(Collectors.joining("; "));

            assertThat(errosNaoEncontrados).hasSize(0).withFailMessage("Não encontrado: '%s'", naoEncontrados);
            assertThat(errosGerados.size()).isEqualTo(errosEsperados.size());
        };
    }

    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }
}
