package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Order;
import com.chervonnaya.objectmappermvcproject.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ObjectMapper mapper;

    @Autowired
    public OrderController(OrderServiceImpl orderService, ObjectMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/{id}")
    public String getOrder(@PathVariable(name = "id") Long id) throws JsonProcessingException {
        Order order = orderService.getById(id);
        return mapper.writeValueAsString(order);
    }

    @GetMapping
    public String getOrders(Pageable pageable) throws JsonProcessingException {
        Page<Order> page = orderService.findAll(pageable);
        return mapper.writeValueAsString(page);
    }

    @PostMapping

    public String createOrder(@RequestBody String json) throws JsonProcessingException {
        Order order = mapper.readValue(json, Order.class);
        Order savedOrder = orderService.save(order);
        return mapper.writeValueAsString(savedOrder);
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable(name = "id") Long id, @RequestBody String json) throws JsonProcessingException {
        Order order = mapper.readValue(json, Order.class);
        order.setId(id);
        Order savedOrder = orderService.save(order);
        return mapper.writeValueAsString(savedOrder);
    }

    @DeleteMapping("/{id}")
    public Long deleteOrder(@PathVariable(name = "id") Long id) {
        return orderService.delete(id);
    }
}
