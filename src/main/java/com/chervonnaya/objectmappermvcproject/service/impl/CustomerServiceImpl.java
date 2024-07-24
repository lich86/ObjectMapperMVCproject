package com.chervonnaya.objectmappermvcproject.service.impl;

import com.chervonnaya.objectmappermvcproject.model.Customer;
import com.chervonnaya.objectmappermvcproject.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends CrudServiceImpl<Customer, CustomerRepository> {

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository, Customer.class);
    }
}
