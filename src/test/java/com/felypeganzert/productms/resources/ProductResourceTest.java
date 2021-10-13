package com.felypeganzert.productms.resources;

import static com.felypeganzert.productms.util.ResponseBodyMatchers.responseBody;
import static com.felypeganzert.productms.utils.AppConstantes.ID;
import static com.felypeganzert.productms.utils.AppConstantes.PRODUCT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.productms.dtos.ProductDTO;
import com.felypeganzert.productms.exceptions.RecursoNaoEncontradoException;
import com.felypeganzert.productms.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductResource.class)
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService service;

    private static final String BASE_PATH = "/products";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        Mockito.when(service.insert(any())).thenReturn(criarProductDTOValido());
    }

    @Test
    void deveRetornarStatus201EChamarInsertDoServiceComSucessoQuandoValido() throws JsonProcessingException, Exception {
        ProductDTO dto = criarProductDTOValido();

        mockMvc.perform(post(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(responseBody().contemObjetoComoJson(criarProductDTOValido(), ProductDTO.class));

        verify(service, times(1)).insert(any());
    }

    @Test
    void deveRetornarStatus400EErroAoCriarComCamposInvalidos() throws Exception {
        ProductDTO dto = new ProductDTO();

        String erroEsperado = "Name é obrigatório; Description é obrigatório; Preço deve ser positivo";

        mockMvc.perform(post(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().contemErro(erroEsperado));
    }

    
    @Test
    void deveRetornarStatus200EChamarUpdateDoServiceComSucessoQuandoValido() throws Exception {
        ProductDTO dto = criarProductDTOValido();

        mockMvc.perform(put(BASE_PATH + "/{id}", 1L)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(service, times(1)).update(anyLong(), any());
    }

    @Test
    void deveRetornarStatus400EErroAoAtualizarComCamposInvalidos() throws Exception {
        ProductDTO dto = new ProductDTO();

        String erroEsperado = "Name é obrigatório; Description é obrigatório; Preço deve ser positivo";

        mockMvc.perform(put(BASE_PATH + "/{id}", 1L)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().contemErro(erroEsperado));
    }

    @Test
    void deveRetornarStatus200AoBuscarPorIdExistente() throws Exception {
        ProductDTO dto = new ProductDTO();
        Long id = 1L;

        Mockito.when(service.findById(id)).thenReturn(dto);
       
        mockMvc.perform(get(BASE_PATH +"/{id}", id )
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(dto, ProductDTO.class));

        verify(service).findById(id);
    }

    @Test
    void deveRetornarStatus404AoBuscarPorIdNaoExistente() throws Exception {
        Long id = 1L;
        RecursoNaoEncontradoException excEsperada = new RecursoNaoEncontradoException(PRODUCT, ID, id);

        Mockito.when(service.findById(id)).thenThrow(excEsperada);

        mockMvc.perform(get(BASE_PATH +"/{id}", id)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().contemErro(excEsperada.getMessage()));

        verify(service).findById(id);
    }

    @Test
    void deveRetornarStatus200EChamarFindAllDoServiceComSucesso() throws Exception {
        mockMvc.perform(get(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    void deveRetornarStatus200EChamarFindAllWithParametersDoServiceComSucesso() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/search")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAllWithParameters(any());
    }

    @Test
    void deveRetornarStatus200EChamarFindAllPagedDoServiceComSucesso() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/paged")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAllPaged(any());
    }

    @Test
    void deveRetornarStatus200AoDeletar() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete(BASE_PATH +"/{id}", id)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).delete(id);
    }

    private ProductDTO criarProductDTOValido() {
        return ProductDTO.builder().name("Name").description("Description").price(BigDecimal.valueOf(1.0)).build();
    }
}
