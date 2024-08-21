package com.propertymanager.demo.mappers;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserResponse, UserRequest> {

}
