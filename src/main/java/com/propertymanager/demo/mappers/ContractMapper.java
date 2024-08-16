package com.propertymanager.demo.mappers;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper extends GenericMapper<Contract, ContractResponse, ContractRequest> {

    @Override
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "tenant.id", target = "tenantId")
    ContractResponse toDto(Contract entity);

}
