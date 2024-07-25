package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Product;
import com.chervonnaya.objectmappermvcproject.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.chervonnaya.objectmappermvcproject.TestData.product1;
import static com.chervonnaya.objectmappermvcproject.TestData.product2;
import static com.chervonnaya.objectmappermvcproject.TestData.productJson1;
import static com.chervonnaya.objectmappermvcproject.TestData.productURL;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl serviceMock;

    @Test
    void getProduct_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(product1);

        mockMvc.perform(get(productURL + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Fan"))
            .andExpect(jsonPath("$.description").value("desc"))
            .andExpect(jsonPath("$.price").value(300))
            .andExpect(jsonPath("$.stock").value(18));
    }

    @Test
    void getAllProducts_Should_Succeed() throws Exception {
        Page<Product> productsPage = new PageImpl<>(List.of(product1, product2),
            PageRequest.of(0, 10), 2);

        when(serviceMock.findAll(PageRequest.of(0, 10))).thenReturn(productsPage);

        mockMvc.perform(get(productURL)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postProduct_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(product1);

        mockMvc.perform(post(productURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson1))
            .andExpect(status().isOk());
    }

    @Test
    void postEmptyProduct_Should_Fail() throws Exception {
        mockMvc.perform(post(productURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void putProduct_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(product1);

        mockMvc.perform(put(productURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson1))
            .andExpect(status().isOk());
    }

    @Test
    void putProduct_Should_Fail() throws Exception {
        mockMvc.perform(put(productURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void deleteProduct_Should_Succeed() throws Exception {
        when(serviceMock.delete(any())).thenReturn(1L);

        mockMvc.perform(delete(productURL + "/1"))
            .andExpect(status().isOk());
    }
}
