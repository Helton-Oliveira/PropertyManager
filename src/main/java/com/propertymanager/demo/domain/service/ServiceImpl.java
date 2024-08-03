package com.propertymanager.demo.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.propertymanager.demo.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public class ServiceImpl<T, ID, R, M> implements IService<T, ID, R, M> {

    @Autowired
    private JpaRepository<T, ID> repository;

    private final ObjectMapper mapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private final Class<R> data;
    private final Class<T> entityClass;

    public ServiceImpl(Class<R> data, Class<T> entityClass) {
        this.data = data;
        this.entityClass = entityClass;
    }

    @Override
    public Page<R> findAll(Pageable page) {
        return repository.findAll(page)
                .map(e -> mapper.convertValue(e, data));
    }
    @Override
    public R findById(ID id) {
        return mapper.convertValue(repository.getReferenceById(id), data);
    }

    @Override
    public R save(M req) {
        System.out.println(entityClass);
        T entity = mapper.convertValue(req, entityClass);
        repository.save(entity);
        return mapper.convertValue(entity, data);
    }

    @Override
    public R update(ID id, M req) {
        T entity = repository.getReferenceById(id);
        T dataForUpdate = mapper.convertValue(req, entityClass);

        BeanUtil.copyNonNullProperties(dataForUpdate, entity);
        T updatedEntity = repository.save(entity);
        return mapper.convertValue(updatedEntity, data);
    }

    @Override
    public boolean delete(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

