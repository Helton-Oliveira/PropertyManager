package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.mappers.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OwnerService extends ServiceImpl<Owner, Long,UserResponse, UserRequest> {
    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerService(OwnerMapper mapper) {
        super(Owner.class, UserResponse.class, mapper);
    }

    @Override
    public boolean delete(Long id) {
        if (ownerRepository.existsById(id)) {
            var entity = ownerRepository.getReferenceById(id);
            entity.setActive(false);
            ownerRepository.save(entity);
            return true;
        }
        return false;
    }

}
