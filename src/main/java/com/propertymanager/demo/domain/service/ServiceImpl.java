package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.database.repository.custom.RepositoryCustom;
import com.propertymanager.demo.mappers.GenericMapper;
import com.propertymanager.demo.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public abstract class ServiceImpl<T, ID, R, M> implements IService<T, ID, R, M> {

    @Autowired
    private JpaRepository<T, ID> repository;

    @Autowired
    private RepositoryCustom<T> custom;

    private final Class<T> entityClass;
    private final Class<R> dtoClass;

    private final GenericMapper<T, R, M> mapper;

    public ServiceImpl(Class<T> entityClass, Class<R> dtoClass, GenericMapper<T, R, M> mapper) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.mapper = mapper;
    }

    @Override
    public Page<R> findAll(Pageable page) {
        return repository.findAll(page)
                .map(mapper::toDto);
    }

    @Override
    public R findById(ID id) {
        return mapper.toDto(repository.getReferenceById(id));
    }

    @Override
    public R save(M req) {
        T entity = mapper.toEntity(req);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public R update(ID id, M req) {
        T entity = repository.getReferenceById(id);
        T dataForUpdate = mapper.toEntity(req);

        BeanUtil.copyNonNullProperties(dataForUpdate, entity);
        T updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public boolean delete(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<R> findByCriteria(Map<String, String> queryParams, Pageable page) {
        var query = custom.searchByCriteria(entityClass, queryParams, page);
        return query.map(mapper::toDto);
    }

    @Override
    public boolean existById(ID id) {
        return repository.existsById(id);
    }
}


