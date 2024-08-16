package com.propertymanager.demo.mappers;


import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper extends GenericMapper<Tenant, UserResponse, UserRequest> {
}
