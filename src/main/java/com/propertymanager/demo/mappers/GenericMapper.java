package com.propertymanager.demo.mappers;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.dtos.UserResponse;
import org.mapstruct.Mapping;

public interface GenericMapper<T, R, M> {

    R toDto(T entity);

    T toEntity(M dto);

}

