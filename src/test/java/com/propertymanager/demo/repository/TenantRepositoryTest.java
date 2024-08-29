package com.propertymanager.demo.repository;

import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.database.repository.TenantRepository;
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
public class TenantRepositoryTest {

    @Autowired
    private TenantRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Page<UserResponse> pageResponse;
    private final Pageable page = PageRequest.of(0, 10, Sort.by("id").ascending());

    private final UserResponse user = new UserResponse(null, "Jose Cunha", "jose.test@example.com", "2831333558","95275000014",  "ADMIN", "senha123");
    private final Tenant tenant = new Tenant(this.user);

    @BeforeEach
    void setup() {
        entityManager.persist(this.tenant);
        this.pageResponse = new PageImpl<>(List.of(new UserResponse(this.tenant)));
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
        var entity = repository.getReferenceById(this.tenant.getId());

        assertThat(entity).isEqualTo(this.tenant);
    }

    @Test
    @DisplayName("must return the user that was inserted into the database")
    void third_scenario() {
        UserResponse savedUserResponse = new UserResponse( null, "Jose Carlos", "carlos.test@example.com", "2831333358","95275000114",  "TENANT", "senha123");
        Tenant savedTenant = new Tenant(savedUserResponse);

        repository.save(savedTenant);

        assertThat(savedTenant.getId()).isNotNull();
    }

    @Test
    @DisplayName("must check if any of the user fields have been updated")
    void fourth_scenario() {
        var tenantToUpdate = repository.findById(this.tenant.getId()).orElseThrow();

        tenantToUpdate.setName("Karlos Kunha");
        repository.save(tenantToUpdate);

        var updatedTenant = repository.findById(tenantToUpdate.getId()).orElseThrow();

        assertThat(updatedTenant.getId()).isEqualTo(this.tenant.getId());
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
        repository.delete(this.tenant);

        var deletedEntity = repository.findById(this.tenant.getId()).orElse(null);

        assertThat(deletedEntity).isNull();
    }

    @Test
    @DisplayName("should return a validation exception if the value of a field of the entity for refund")
    @Transactional
    void sixth_scenario() {
        var userExist = new UserResponse( null, "Wagner Jose", "wagner.test@example.com", "2831333578","95275000014",  "TENANT", "senha123");
        Tenant existingUser = new Tenant(userExist);
        repository.save(existingUser);

        var newUser = new UserResponse( null, "Wagner Jose", "wagner.test@example.com", "2831333568","95275000014",  "TENANT", "senha123");
        Tenant newTenant = new Tenant(newUser);

        var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(newTenant);
        });

        System.out.println(exception.getMessage());

        assertThat(exception.getMessage()).contains("could not execute statement [ERRO: duplicar valor da chave viola a restrição de unicidade \"users_email_key");
    }

}

