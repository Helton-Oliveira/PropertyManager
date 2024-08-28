package com.propertymanager.demo.controller;

import com.propertymanager.demo.domain.dtos.AuthenticationDto;
import com.propertymanager.demo.domain.service.AuthorizationService;
import com.propertymanager.demo.domain.service.UserService;
import com.propertymanager.demo.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AuthenticationDto> reqTester;

    @MockBean
    private TokenService service;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthorizationService authService;

    private AuthenticationDto dto;

    @BeforeEach
    public void setup() {
        this.dto = new AuthenticationDto("email@test.com", "senha1234");
    }

    @Test
    @DisplayName("should return an http status 200 and a valid token")
    void first_scenario() throws Exception {

        com.propertymanager.demo.domain.database.entity.User user = new com.propertymanager.demo.domain.database.entity.User();
        user.setEmail("email@test.com");
        user.setPassword("senha1234");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Mockando TokenService
        when(service.generateToken(user))
                .thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlbWFpbEB0ZXN0LmNvbSIsInBhc3N3b3JkIjoic2VuaGExMjM0IiwiaWF0IjoxNTE2MjM5MDIyfQ.0nidDmlc18nHwNCMxmsRgLRwRBlEkQhCvY9xdfohixs");

        var response = mvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqTester.write(this.dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
    }
}
