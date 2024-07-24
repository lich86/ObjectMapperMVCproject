package com.chervonnaya.objectmappermvcproject.repository;

import com.chervonnaya.objectmappermvcproject.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<T extends BaseEntity>  extends JpaRepository<T, Long> {
}
