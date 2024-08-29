package com.propertymanager.demo.repository;

import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.OwnerRepository;
import com.propertymanager.demo.domain.dtos.UserResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Page<UserResponse> pageResponse;
    private final Pageable page = PageRequest.of(0, 10, Sort.by("id").ascending());

    private final UserResponse user = new UserResponse(null, "Jose Cunha", "jose.test@example.com", "2831333558","95275000014",  "OWNER", "senha123");
    private final Owner owner = new Owner(this.user);

    @BeforeEach
    void setup() {
        entityManager.persist(this.owner);
        this.pageResponse = new PageImpl<>(List.of(new UserResponse(this.owner)));
    }


    @Test
    @DisplayName("must return a page of the user entity")
    void first_scenario() {

        var listOfUsers = repository.findAll(page)
                .map(UserResponse::new);

        assertThat(listOfUsers)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(this.pageResponse);

    }

    @Test
    @DisplayName("should return only one user")
    void second_scenario() {
        var entity = repository.getReferenceById(this.owner.getId());

        assertThat(entity).isEqualTo(this.owner);
    }

    @Test
    @DisplayName("must return the user that was inserted into the database")
    void third_scenario() {
        UserResponse savedUserResponse = new UserResponse( null, "Jose Carlos", "carlos.test@example.com", "2831333358","95275000114",  "OWNER", "senha123");
        Owner savedOwner = new Owner(savedUserResponse);

        repository.save(savedOwner);

        assertThat(savedOwner.getId()).isNotNull();
    }

    @Test
    @DisplayName("must check if any of the user fields have been updated")
    void fourth_scenario() {
        var ownerToUpdate = repository.findById(this.owner.getId()).orElseThrow();

        ownerToUpdate.setName("Karlos Kunha");
        repository.save(ownerToUpdate);

        var updatedTenant = repository.findById(ownerToUpdate.getId()).orElseThrow();

        assertThat(updatedTenant.getId()).isEqualTo(this.owner.getId());
        assertThat(updatedTenant.getName()).isEqualTo("Karlos Kunha");
        assertThat(updatedTenant.getCpf()).isNotNull();
        assertThat(updatedTenant.getRole()).isNotNull();
        assertThat(updatedTenant.getPhone()).isNotNull();
        assertThat(updatedTenant.getPassword()).isNotNull();
        assertThat(updatedTenant.getEmail()).isNotNull();
    }

    @Test
    @DisplayName("deve retornar um null caso o usuário for excluído")
    void fifth_scenario() {
        repository.delete(this.owner);

        var deletedEntity = repository.findById(this.owner.getId()).orElse(null);

        assertThat(deletedEntity).isNull();
    }

    @Test
    @DisplayName("should return a validation exception if the value of a field of the entity for refund")
    @Transactional
    void sixth_scenario() {
        var userExist = new UserResponse( null, "Wagner Jose", "wagner.test@example.com", "2831333578","95275000014",  "OWNER", "senha123");
        Owner existingOwner = new Owner(userExist);
        repository.save(existingOwner);

        var newUser = new UserResponse( null, "Wagner Jose", "wagner.test@example.com", "2831333568","95275000014",  "OWNER", "senha123");
        Owner newOwner = new Owner(newUser);

        var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(newOwner);
        });

        assertThat(exception.getMessage()).contains("could not execute statement [ERRO: duplicar valor da chave viola a restrição de unicidade \"users_email_key");
    }

}

