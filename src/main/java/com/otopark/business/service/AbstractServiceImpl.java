package com.otopark.business.service;

import com.otopark.business.model.Persistable;
import com.otopark.business.repository.AbstractJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Locale;

public class AbstractServiceImpl<T extends Persistable> implements AbstractService<T>{

    private MessageSource messageSource;
    private AbstractJPARepository abstractJPARepository;

    public AbstractServiceImpl(AbstractJPARepository baseDAORepository) {
        this.abstractJPARepository = baseDAORepository;
    }

    @Autowired
    public final void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Long count() {
        return abstractJPARepository.count();
    }

    @Override
    public Long count(Specification<? extends Persistable> specification) {
        return abstractJPARepository.count(specification);
    }

    @Override
    public T findById(Long id) {
        return (T) abstractJPARepository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return abstractJPARepository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return abstractJPARepository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Specification<? extends Persistable> specification) {
        return abstractJPARepository.findAll(specification);
    }

    @Override
    public Page<T> findAll(Specification<? extends Persistable> specification, Pageable pageable) {
        return abstractJPARepository.findAll(specification, pageable);
    }

    @Override
    public synchronized T save(T entity) {
        return (T) abstractJPARepository.save(entity);
    }

    @Override
    public synchronized T saveAndFlush(T entity) {
        return (T) abstractJPARepository.saveAndFlush(entity);
    }

    @Override
    public synchronized void saveAll(Iterable<T> entities) {
        abstractJPARepository.saveAll(entities);
    }

    @Override
    public synchronized void delete(T entity) {
        abstractJPARepository.deleteById(entity.getId());
    }

    @Override
    public synchronized void deleteById(Long id) {
        abstractJPARepository.deleteById(id);
    }

    public String getMessage(String key) {
        return getMessage(key,null, Locale.getDefault());
    }

    public String getMessage(String key, Object[] params) {
        return getMessage(key, params, Locale.getDefault());
    }

    public String getMessage(String key, Locale locale) {
        return getMessage(key,null, locale);
    }

    public String getMessage(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }
}
