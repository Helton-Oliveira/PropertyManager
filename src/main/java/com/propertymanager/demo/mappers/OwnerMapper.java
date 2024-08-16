package com.propertymanager.demo.mappers;

import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper extends GenericMapper<Owner, UserResponse, UserRequest> {
}
