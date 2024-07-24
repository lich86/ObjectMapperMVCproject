package com.chervonnaya.objectmappermvcproject.service.impl;

import com.chervonnaya.objectmappermvcproject.model.Order;
import com.chervonnaya.objectmappermvcproject.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, OrderRepository> {

    public OrderServiceImpl(OrderRepository repository) {
        super(repository, Order.class);
    }
}
