package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PropertyService extends ServiceImpl<Property, Long, PropertyResponse, PropertyRequest> {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public PropertyService() {
        super(PropertyResponse.class, Property.class);
    }

    @Override
    public PropertyResponse save(PropertyRequest req) {
        var owner = ownerRepository.findById(req.getOwnerId());
        if (owner.isPresent()) {
            var property = new Property(req, owner.get());
            propertyRepository.save(property);
            return new PropertyResponse(property, req, owner.get());
        }
        return null;
    }

    @Override
    public Page<PropertyResponse> findAll(Pageable page) {
        return propertyRepository.findAll(page)
                .map(PropertyResponse::new);
    }

    public Page<PropertyResponse> filterByLocation(Map<String, String> req, Pageable page) {
        Map<String, String> modifiedParams = req.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> "address." + entry.getKey(),
                        Map.Entry::getValue
                ));
        return super.findByCriteria(modifiedParams, page);
    }

}
