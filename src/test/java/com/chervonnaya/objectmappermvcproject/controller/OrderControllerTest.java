package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Order;
import com.chervonnaya.objectmappermvcproject.service.impl.OrderServiceImpl;
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

import static com.chervonnaya.objectmappermvcproject.TestData.order1;
import static com.chervonnaya.objectmappermvcproject.TestData.order2;
import static com.chervonnaya.objectmappermvcproject.TestData.orderJson1;
import static com.chervonnaya.objectmappermvcproject.TestData.orderURL;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl serviceMock;

    @Test
    void getOrder_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(order1);

        mockMvc.perform(get(orderURL + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.address.city").value("Anytown"))
            .andExpect(jsonPath("$.address.street").value("Main St"))
            .andExpect(jsonPath("$.address.home").value(99))
            .andExpect(jsonPath("$.address.apartment").value("18"))
            .andExpect(jsonPath("$.products[0].name").value("Fan"))
            .andExpect(jsonPath("$.products[0].description").value("desc"))
            .andExpect(jsonPath("$.products[0].price").value(300))
            .andExpect(jsonPath("$.products[0].stock").value("18"))
            .andExpect(jsonPath("$.sum").value(300))
            .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void getAllOrders_Should_Succeed() throws Exception {
        Page<Order> ordersPage = new PageImpl<>(List.of(order1, order2),
            PageRequest.of(0, 10), 2);

        when(serviceMock.findAll(PageRequest.of(0, 10))).thenReturn(ordersPage);

        mockMvc.perform(get(orderURL)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postOrder_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(order1);

        mockMvc.perform(post(orderURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson1))
            .andExpect(status().isOk());
    }

    @Test
    void postEmptyOrder_Should_Fail() throws Exception {
        mockMvc.perform(post(orderURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void putOrder_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(order1);

        mockMvc.perform(put(orderURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson1))
            .andExpect(status().isOk());
    }

    @Test
    void putOrder_Should_Fail() throws Exception {
        mockMvc.perform(put(orderURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void deleteOrder_Should_Succeed() throws Exception {
        when(serviceMock.delete(any())).thenReturn(1L);

        mockMvc.perform(delete(orderURL + "/1"))
            .andExpect(status().isOk());
    }
}
