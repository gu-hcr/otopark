package com.otopark.business.service;

import com.otopark.business.model.Persistable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AbstractService<T extends Persistable> {
    Long count();
    Long count(Specification<? extends Persistable> specification);
    T findById(Long primaryKey);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    List<T> findAll(Specification<? extends Persistable> specification);
    Page<T> findAll(Specification<? extends Persistable> specification, Pageable pageable);
    T save(T entity);
    T saveAndFlush(T entity);
    void saveAll(Iterable<T> entities);
    void delete(T entity);
    void deleteById(Long id);
}
