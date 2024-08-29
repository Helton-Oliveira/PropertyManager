package com.propertymanager.demo.repository;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.database.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Page<UserResponse> pageResponse;
    private final Pageable page = PageRequest.of(0, 10, Sort.by("id").ascending());

    private final User user = new User(null, "Jose Cunha", "jose.test@example.com", "senha123", "95275000014", "2831333558", true, "ADMIN");

    @BeforeEach
    void setup() {
        entityManager.persist(this.user);
        this.pageResponse = new PageImpl<>(List.of(new UserResponse(user)));
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
        var entity = repository.getReferenceById(this.user.getId());

        assertThat(entity).isEqualTo(this.user);
    }

    @Test
    @DisplayName("must return the user that was inserted into the database")
    void third_scenario() {
        User savedUser = new User(null, "Joseneide", "joseneide.test@example.com", "senha123", "95275000114", "2831333588", true, "ADMIN");

        repository.save(savedUser);

        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("must check if any of the user fields have been updated")
    void fourth_scenario() {
        var userToUpdate = repository.findById(this.user.getId()).orElseThrow();

        userToUpdate.setName("Karlos Kunha");
        repository.save(userToUpdate);

        var updatedUser = repository.findById(userToUpdate.getId()).orElseThrow();

        assertThat(updatedUser.getId()).isEqualTo(this.user.getId());
        assertThat(updatedUser.getName()).isEqualTo("Karlos Kunha");
        assertThat(updatedUser.getCpf()).isNotNull();
        assertThat(updatedUser.getRole()).isNotNull();
        assertThat(updatedUser.getPhone()).isNotNull();
        assertThat(updatedUser.getPassword()).isNotNull();
        assertThat(updatedUser.getEmail()).isNotNull();
    }

    @Test
    @DisplayName("should return null if the user is deleted")
    void fifth_scenario() {
        repository.delete(this.user);

        var deletedEntity = repository.findById(this.user.getId()).orElse(null);

        assertThat(deletedEntity).isNull();
    }

    @Test
    @DisplayName("should return a validation exception if the value of a field of the entity for refund")
    @Transactional
    void sixth_scenario() {
        User existingUser = new User(null, "Mario Campos", "mario.test@example.com", "senha1", "9527500000", "2831333500", true, "ADMIN");
        repository.save(existingUser);

        User newUser = new User(null, "Karla", "mario.test@example.com", "senha12", "9527500044", "2831333550", true, "ADMIN");

        var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(newUser);
        });

        System.out.println(exception.getMessage());

        assertThat(exception.getMessage()).contains("could not execute statement [ERRO: duplicar valor da chave viola a restrição de unicidade \"users_email_key");
    }

}

