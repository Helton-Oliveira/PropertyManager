package com.propertymanager.demo.repository;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.ContractRepository;
import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.database.repository.PropertyRepository;
import com.propertymanager.demo.domain.database.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class ContractRepositoryTest {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ContractRepository repository;

    private Tenant tenant;
    private Owner owner;
    private Property property;
    private Contract contract;
    private List<Contract> contracts;

    @BeforeEach
    void setup() {
        tenant = createTenant();
        owner = createOwner();
        property = createProperty(owner);
        contract = createContract(property, tenant);
        contracts = createContractsList(property, tenant, contract);

        tenantRepository.save(tenant);
        ownerRepository.save(owner);
        propertyRepository.save(property);
        repository.save(contract);
        repository.save(contracts.get(1));
    }

    @Test
    @DisplayName("should return a list of contracts")
    void first_scenario() {
        var contractList = repository.findAll()
                .stream().map(Contract::new);

        assertThat(contractList)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(this.contracts);
    }

    @Test
    @DisplayName("should return only one contract")
    void second_scenario() {
        var contract = repository.getReferenceById(this.contract.getId());

        assertThat(contract).isEqualTo(this.contract);
    }

    @Test
    @DisplayName("must return the contract that was inserted into the database")
    void third_scenario() {
        Contract saveedContract = createContract(this.property, this.tenant);

        repository.save(saveedContract);

        assertThat(saveedContract.getId()).isNotNull();
    }

    private Contract createContract(Property property, Tenant tenant) {
        Contract contract = new Contract();
        contract.setId(null);
        contract.setTenant(tenant);
        contract.setProperty(property);
        contract.setCreated(LocalDateTime.of(2024, 8, 29, 15, 49));
        contract.setStatus(true);
        contract.setValidity(LocalDate.of(2027, 8, 28));
        return contract;
    }

    private List<Contract> createContractsList(Property property, Tenant tenant, Contract firstContract) {
        List<Contract> contracts = new ArrayList<>();
        contracts.add(firstContract);

        Contract secondContract = createContract(property, tenant);
        secondContract.setCreated(LocalDateTime.of(2025, 8, 30, 10, 00));
        secondContract.setValidity(LocalDate.of(2028, 8, 29));

        contracts.add(secondContract);

        return contracts;
    }

    private Tenant createTenant() {
        Tenant tenant = new Tenant();
        tenant.setName("Steve Rogers");
        tenant.setEmail("Steve.Rogers@test.com");
        tenant.setCpf("53082202047");
        tenant.setPassword("senha123");
        tenant.setPhone("2733827817");
        tenant.setActive(true);
        tenant.setRole("TENANT");
        return tenant;
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
