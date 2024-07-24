package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Customer;
import com.chervonnaya.objectmappermvcproject.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final ObjectMapper mapper;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, ObjectMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public String getCustomer(@PathVariable(name = "id") Long id) throws JsonProcessingException {
        Customer customer = customerService.getById(id);
        return mapper.writeValueAsString(customer);
    }

    @GetMapping
    public String getCustomers(Pageable pageable) throws JsonProcessingException {
        Page<Customer> page = customerService.findAll(pageable);
        return mapper.writeValueAsString(page);
    }

    @PostMapping
    public String createCustomer(@RequestBody String json) throws JsonProcessingException {
        Customer customer = mapper.readValue(json, Customer.class);
        Customer savedCustomer = customerService.save(customer);
        return mapper.writeValueAsString(savedCustomer);
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable(name = "id") Long id, @RequestBody String json) throws JsonProcessingException {
        Customer customer = mapper.readValue(json, Customer.class);
        customer.setId(id);
        Customer savedCustomer = customerService.save(customer);
        return mapper.writeValueAsString(savedCustomer);
    }

    @DeleteMapping("/{id}")
    public Long deleteCustomer(@PathVariable(name = "id") Long id) {
        return customerService.delete(id);
    }
}

