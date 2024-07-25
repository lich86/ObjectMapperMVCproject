package com.chervonnaya.objectmappermvcproject.service;

import com.chervonnaya.objectmappermvcproject.model.Product;
import com.chervonnaya.objectmappermvcproject.repository.ProductRepository;
import com.chervonnaya.objectmappermvcproject.service.impl.ProductServiceImpl;
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

import static com.chervonnaya.objectmappermvcproject.TestData.product1;
import static com.chervonnaya.objectmappermvcproject.TestData.product2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getById_Should_Succeed() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product1));

        assertEquals(product1, productService.getById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getAll_Should_Succeed() {
        Page<Product> productsPage = new PageImpl<>(List.of(product1, product2), PageRequest.of(0, 10), 2);
        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(productsPage);

        assertEquals(productsPage, productService.findAll(PageRequest.of(0, 10)));
        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void save_Should_Succeed() {
        when(productRepository.save(any())).thenReturn(product1);

        productService.save(product1);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void update_Should_Succeed() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(productRepository.save(any())).thenReturn(product1);

        productService.update(1L, product1);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void delete_Should_Succeed() {
        product1.setId(1L);
        doNothing().when(productRepository).deleteById(product1.getId());

        productService.delete(product1.getId());

        verify(productRepository, times(1)).deleteById(product1.getId());
    }
}
