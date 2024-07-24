package com.chervonnaya.objectmappermvcproject.service;

import com.chervonnaya.objectmappermvcproject.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService <E extends BaseEntity> {

    E getById(Long id);

    E save(E e);

    Long delete(Long id);

    E update(Long id, E e);

    Page<E> findAll(Pageable pageable);

    List<E> findAll();
}
