package com.propertymanager.demo.Service;

import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.service.PropertyService;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class PropertyServiceTest {

    @MockBean
    private PropertyService service;

    @Test
    @DisplayName("should return error if property value is 0 or less")
    @WithMockUser("ADMIN")
    void first_scenario() {
        var property = mock(PropertyRequest.class);
        property.setRentalValue("0");

        when(service.save(property)).thenThrow(new ValidateException("ERROR! Property value cannot be equal to or less than 0"));

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            service.save(property);
        });

        assertThat(exception.getMessage()).isEqualTo("ERROR! Property value cannot be equal to or less than 0");
    }

    @Test
    @DisplayName("should return error if owner is inactive")
    @WithMockUser("ADMIN")
    void second_scenario() {
        var property = mock(PropertyRequest.class);
        property.setOwnerId(17L);

        when(service.save(property)).thenThrow(new ValidateException("ERROR! Provided owner is not active in the system"));

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            service.save(property);
        });

        assertThat(exception.getMessage()).isEqualTo("ERROR! Provided owner is not active in the system");
    }
}
