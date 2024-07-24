package com.chervonnaya.objectmappermvcproject.service.impl;

import com.chervonnaya.objectmappermvcproject.model.Product;
import com.chervonnaya.objectmappermvcproject.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CrudServiceImpl<Product, ProductRepository> {

    public ProductServiceImpl(ProductRepository repository) {
        super(repository, Product.class);
    }
}
