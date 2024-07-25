package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Customer;
import com.chervonnaya.objectmappermvcproject.service.impl.CustomerServiceImpl;
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

import static com.chervonnaya.objectmappermvcproject.TestData.customer1;
import static com.chervonnaya.objectmappermvcproject.TestData.customer2;
import static com.chervonnaya.objectmappermvcproject.TestData.customerJson1;
import static com.chervonnaya.objectmappermvcproject.TestData.customerURL;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl serviceMock;

    @Test
    void getCustomer_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(customer1);

        mockMvc.perform(get(customerURL + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.email").value("john.doe@example.com"))
            .andExpect(jsonPath("$.password").doesNotExist());
    }


    @Test
    void getAllCustomers_Should_Succeed() throws Exception {
        Page<Customer> customersPage = new PageImpl<>(List.of(customer1, customer2),
            PageRequest.of(0, 10), 2);

        when(serviceMock.findAll(PageRequest.of(0, 10))).thenReturn(customersPage);

        mockMvc.perform(get(customerURL)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postCustomer_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(customer1);

        mockMvc.perform(post(customerURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson1))
            .andExpect(status().isOk());
    }

    @Test
    void postEmptyCustomer_Should_Fail() throws Exception {
        mockMvc.perform(post(customerURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }


    @Test
    void putCustomer_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(customer1);

        mockMvc.perform(put(customerURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson1))
            .andExpect(status().isOk());
    }

    @Test
    void putCustomer_Should_Fail() throws Exception {
        mockMvc.perform(put(customerURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void deleteCustomer_Should_Succeed() throws Exception {
        when(serviceMock.delete(any())).thenReturn(1L);

        mockMvc.perform(delete(customerURL + "/1"))
            .andExpect(status().isOk());
    }
}

