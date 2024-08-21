package com.propertymanager.demo.mappers;

public interface GenericMapper<T, R, M> {

    R toDto(T entity);

    T toEntity(M dto);

}

