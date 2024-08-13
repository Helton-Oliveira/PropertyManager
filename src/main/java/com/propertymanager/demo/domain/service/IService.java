package com.propertymanager.demo.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Service
public interface IService<T, ID, R, M>{
    Page<R> findAll(Pageable p) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    R findById(ID id);
    R save(M req) throws JsonProcessingException;
    R update(ID id, M req) throws JsonProcessingException;
    boolean delete(ID id);
    Page<R> findByCriteria(Map<String, String> queryParams, Pageable page);
}
