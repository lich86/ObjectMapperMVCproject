package com.chervonnaya.objectmappermvcproject.service.impl;

import com.chervonnaya.objectmappermvcproject.exception.EntityNotFoundException;
import com.chervonnaya.objectmappermvcproject.exception.SaveEntityException;
import com.chervonnaya.objectmappermvcproject.exception.UnableToDeleteException;
import com.chervonnaya.objectmappermvcproject.model.BaseEntity;
import com.chervonnaya.objectmappermvcproject.repository.IRepository;
import com.chervonnaya.objectmappermvcproject.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CrudServiceImpl<E extends BaseEntity, R extends IRepository<E>> implements CrudService<E> {

    final R repository;
    final Class<E> genericType;

    @Autowired
    public CrudServiceImpl(R repository, Class<E> genericType) {
        this.repository = repository;
        this.genericType = genericType;
    }

    @Override
    public E getById(Long id) {
        Optional<E> entity = repository.findById(id);
        if(entity.isPresent()) {
            log.info(String.format("Found %s with id %s",
                this.genericType.getSimpleName().toLowerCase(), id));
            return entity.get();
        }
        log.error(String.format("Unable to get %s with id %s",
            this.genericType.getSimpleName().toLowerCase(), id));
        throw new EntityNotFoundException(this.genericType.getSimpleName(), id);
    }

    @Transactional
    @Override
    public E save(E e) {
        try {
            E entity = repository.save(e);
            log.info(String.format("Saved %s, id: %d",
                this.genericType.getSimpleName().toLowerCase(), entity.getId()));
            return entity;
        } catch (Exception ex) {
            log.error(String.format("Unable to save %s", this.genericType.getSimpleName().toLowerCase()));
            String errorMessage;
            if (ex instanceof PropertyValueException || ex instanceof DataIntegrityViolationException) {
                errorMessage = "Please provide correct data";
            } else {
                errorMessage = ex.getMessage();
            }
            throw new SaveEntityException(this.genericType.getSimpleName(), errorMessage);
        }
    }

    @Transactional
    @Override
    public E update(Long id, E e) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException(this.genericType.getSimpleName(), id));
        try {
            e.setId(id);
            E entity = repository.save(e);
            log.info(String.format("Updated %s, id: %d",
                this.genericType.getSimpleName().toLowerCase(), entity.getId()));
            return entity;
        } catch (Exception ex) {
            log.error(String.format("Unable to update %s", this.genericType.getSimpleName().toLowerCase()), id);
            throw new SaveEntityException(this.genericType.getSimpleName(), id, ex.getMessage());
        }

    }

    @Transactional
    @Override
    public Long delete(Long id) {
        try {
            repository.deleteById(id);
            log.info(String.format("%s with id %d is deleted",
                this.genericType.getSimpleName(), id));
            return id;
        } catch (Exception ex) {
            log.error(String.format("Unable to delete %s with id %d. Error message: %s",
                this.genericType.getSimpleName().toLowerCase(), id, ex.getMessage()));
            throw new UnableToDeleteException(this.genericType.getSimpleName(), id);
        }
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        Page<E> all = repository.findAll(pageable);
        log.info(String.format("Created page of %ss", this.genericType.getSimpleName().toLowerCase()));
        return all;
    }

    public List<E> findAll() {
        return repository.findAll();
    }
}
