package com.chervonnaya.objectmappermvcproject.service;

import com.chervonnaya.objectmappermvcproject.model.Customer;
import com.chervonnaya.objectmappermvcproject.repository.CustomerRepository;
import com.chervonnaya.objectmappermvcproject.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.chervonnaya.objectmappermvcproject.TestData.customer1;
import static com.chervonnaya.objectmappermvcproject.TestData.customer2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getById_Should_Succeed() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer1));

        assertEquals(customer1, customerService.getById(1L));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getAll_Should_Succeed() {
        Page<Customer> customersPage = new PageImpl<>(List.of(customer1, customer2), PageRequest.of(0, 10), 2);
        when(customerRepository.findAll(PageRequest.of(0, 10))).thenReturn(customersPage);

        assertEquals(customersPage, customerService.findAll(PageRequest.of(0, 10)));
        verify(customerRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void save_Should_Succeed() {
        when(customerRepository.save(any())).thenReturn(customer1);

        customerService.save(customer1);

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void update_Should_Succeed() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any())).thenReturn(customer1);

        customerService.update(1L, customer1);

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void delete_Should_Succeed() {
        customer1.setId(1L);
        doNothing().when(customerRepository).deleteById(customer1.getId());

        customerService.delete(customer1.getId());

        verify(customerRepository, times(1)).deleteById(customer1.getId());
    }
}
