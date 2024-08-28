package com.propertymanager.demo.Service;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.database.repository.UserRepository;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.UserService;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class UserServiceTest {

    @MockBean
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("should return an error if you try to delete the only ADMIN from the system")
    @WithMockUser(roles = "ADMIN")
    void first_scenario() {
        Long adminUserId = 22L;

        User adminUser = new User();
        adminUser.setId(adminUserId);
        adminUser.setRole("ADMIN");

        var userResponse = mock(UserResponse.class);
        userResponse.setId(adminUserId);

        when(repository.countByRole("ADMIN")).thenReturn(1L);
        when(service.findById(adminUserId)).thenReturn(userResponse);

        when(service.delete(adminUserId)).thenThrow(new ValidateException("Cannot delete the only ADMIN user"));

        ValidateException exception = assertThrows(ValidateException.class, () ->
                service.delete(adminUserId)
        );

        assertEquals("Cannot delete the only ADMIN user", exception.getMessage());

        verify(repository, never()).delete(adminUser);
    }
}

