package com.chervonnaya.objectmappermvcproject.controller;

import com.chervonnaya.objectmappermvcproject.model.Product;
import com.chervonnaya.objectmappermvcproject.service.impl.ProductServiceImpl;
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
@RequestMapping("/api/product")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ObjectMapper mapper;

    @Autowired
    public ProductController(ProductServiceImpl productService, ObjectMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/{id}")
    public String getProduct(@PathVariable(name = "id") Long id) throws JsonProcessingException {
        Product product = productService.getById(id);
        return mapper.writeValueAsString(product);
    }

    @GetMapping
    public String getProducts(Pageable pageable) throws JsonProcessingException {
        Page<Product> page = productService.findAll(pageable);
        return mapper.writeValueAsString(page);
    }

    @PostMapping
    public String createProduct(@RequestBody String json) throws JsonProcessingException {
        Product product = mapper.readValue(json, Product.class);
        Product savedProduct = productService.save(product);
        return mapper.writeValueAsString(savedProduct);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable(name = "id") Long id, @RequestBody String json) throws JsonProcessingException {
        Product product = mapper.readValue(json, Product.class);
        product.setId(id);
        Product savedProduct = productService.save(product);
        return mapper.writeValueAsString(savedProduct);
    }

    @DeleteMapping("/{id}")
    public Long deleteProduct(@PathVariable(name = "id") Long id) {
        return productService.delete(id);
    }
}

