package com.propertymanager.demo.domain.database.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RepositoryCustom<T> {
    Page<T> searchByCriteria(Class<T> entityClass, Map<String, String> query, Pageable page);
}
