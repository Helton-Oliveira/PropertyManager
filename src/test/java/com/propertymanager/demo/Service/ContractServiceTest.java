package com.propertymanager.demo.Service;


import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.service.ContractService;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class ContractServiceTest {

    @MockBean
    private ContractService service;

    private  ContractRequest request;

    @Test
    @DisplayName("should return an error when trying to create a new contract with a tenant who already has one open in the period")
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void first_scenario() {
        when(service.save(any(ContractRequest.class))).thenThrow(new ValidateException("Tenant already has an open contract"));

        this.request = new ContractRequest(32L, 28L, 2);

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            service.save(this.request);
        });

        assertThat(exception.getMessage()).isEqualTo("Tenant already has an open contract");
    }

    @Test
    @DisplayName("should return an error when trying to create a new contract with a property that already has an open contract in the period")
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void second_scenario() {
        when(service.save(any(ContractRequest.class))).thenThrow(new ValidateException("Property already has an open contract"));

        this.request = new ContractRequest(23L, 33L, 2);

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            service.save(this.request);
        });

        assertThat(exception.getMessage()).isEqualTo("Property already has an open contract");
    }
}

