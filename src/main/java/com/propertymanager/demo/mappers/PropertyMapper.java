package com.propertymanager.demo.mappers;

import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropertyMapper extends GenericMapper<Property, PropertyResponse, PropertyRequest> {

    @Override
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "address", target = "addressDto")
    PropertyResponse toDto(Property entity);
}

