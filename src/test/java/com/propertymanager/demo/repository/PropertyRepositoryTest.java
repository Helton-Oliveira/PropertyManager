package com.propertymanager.demo.repository;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.database.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class PropertyRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository repository;

    private Owner owner;
    private Property property;
    private Contract contract;
    private List<Property> properties;

    @BeforeEach
    void setup() {
        owner = createOwner();
        property = createProperty(owner);
        properties = createPropertiesList(owner, property);

        ownerRepository.save(owner);
        repository.save(property);
        repository.save(properties.get(1));

    }

    @Test
    @DisplayName("should return a list of properties")
    void first_scenario() {
        var contractList = repository.findAll()
                .stream().map(Property::new);

        assertThat(contractList)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(this.property);
    }

    @Test
    @DisplayName("should return only one property")
    void second_scenario() {
        var property = repository.getReferenceById(this.property.getId());

        assertThat(property).isEqualTo(this.property);
    }

    @Test
    @DisplayName("must return the property that was inserted into the database")
    void third_scenario() {
        Property savedProperty = createProperty(this.owner);

        repository.save(savedProperty);

        assertThat(savedProperty.getId()).isNotNull();
    }

    @Test
    @DisplayName("must check if any of the properties fields have been updated")
    void fourth_scenario() {
        var propertyToUpdate = repository.findById(this.property.getId()).orElseThrow();

        Owner newOwner = createOwner();
        newOwner.setName("Novo proprietario");
        propertyToUpdate.setOwner(newOwner);
        repository.save(propertyToUpdate);

        var updatedProperty = repository.findById(propertyToUpdate.getId()).orElseThrow();

        assertThat(updatedProperty.getId()).isEqualTo(this.property.getId());
        assertThat(updatedProperty.getOwner().getName()).isEqualTo("Novo proprietario");
        assertThat(updatedProperty.getTypeProperty()).isNotNull();
        assertThat(updatedProperty.getRentalValue()).isNotNull();
        assertThat(updatedProperty.getAddress()).isNotNull();
        assertThat(updatedProperty.getRented()).isNotNull();
        assertThat(updatedProperty.getDescription()).isNotNull();

    }

    @Test
    @DisplayName("should return null if the property is deleted")
    void fifth_scenario() {
        repository.delete(this.property);

        var deletedProperty = repository.findById(this.property.getId()).orElse(null);

        assertThat(deletedProperty).isNull();
    }

    @Test
    @DisplayName("should return a validation exception if the value of a field of the entity for refund")
    @Transactional
    void sixth_scenario() {
        Property existingProperty = createProperty(owner);
        repository.save(existingProperty);

        var newProperty = createProperty(owner);
        newProperty.setTypeProperty(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(newProperty);
        });
    }


    private List<Property> createPropertiesList(Owner owner, Property firstProperty) {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(firstProperty);

        Property secondContract = createProperty(owner);
        propertyList.add(secondContract);

        return propertyList;
    }

    private Owner createOwner() {
        Owner owner = new Owner();
        owner.setName("Tony Stark");
        owner.setEmail("Tony.avengers@test.com");
        owner.setCpf("84275963083");
        owner.setPassword("senha123");
        owner.setPhone("8235755790");
        owner.setActive(true);
        owner.setRole("OWNER");
        return owner;
    }

    private Property createProperty(Owner owner) {
        Property property = new Property();
        property.setAddress(new Addres("Avenida das Palmeiras", "Jardim Primavera", "87654321", "456", "Rio de Janeiro", "RJ"));
        property.setTypeProperty(TypeProperty.APARTAMENT);
        property.setRentalValue("500");
        property.setDescription("best visit");
        property.setOwner(owner);
        property.setRented(true);
        return property;
    }

}
