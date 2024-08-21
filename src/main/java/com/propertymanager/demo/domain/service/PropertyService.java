package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.businessRules.propertyRules.PropertyRules;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.repository.PropertyRepository;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import com.propertymanager.demo.mappers.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PropertyService extends ServiceImpl<Property, Long, PropertyResponse, PropertyRequest> {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private List<PropertyRules> validators;

    public PropertyService(PropertyMapper mapper) {
        super(Property.class, PropertyResponse.class, mapper);
    }

    @Override
    public PropertyResponse save(PropertyRequest req) {
        var owner = ownerService.findById(req.getOwnerId());
        if (owner == null) {
            throw new ValidateException("ERRO! ID do proprietário informado não existe.");
        }

        validators.forEach(v -> v.valid(req));

        var property = new Property(req, owner);
        propertyRepository.save(property);
        return new PropertyResponse(property, req);
    }


    public Page<PropertyResponse> filterByLocation(Map<String, String> req, Pageable page) {
        Map<String, String> modifiedParams = req.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> "address." + entry.getKey(),
                        Map.Entry::getValue
                ));
        return super.findByCriteria(modifiedParams, page);
    }

    public void upgradeToRented(Property property) {
        property.toHire();
        propertyRepository.save(property);
    }

    public Property getReferencePropertyById(Long id) {
        if(!propertyRepository.existsById(id)) {
            throw new ValidateException("ERRO! ID da propriedade informada nào existe");
        }
        return propertyRepository.getReferenceById(id);
    }

}
