package com.propertymanager.demo.controller;

import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.TenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class TenantControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserResponse> resTester;

    @Autowired
    private JacksonTester<UserRequest> reqTester;

    @Autowired
    private JacksonTester<Page<UserResponse>> pageUserResponseTester;

    @MockBean
    private TenantService service;

    private Tenant tenant;
    private UserRequest request;
    private UserResponse userResponse;
    private Page<UserResponse> pageResponse;
    private UserResponse updatedUser;

   @BeforeEach
     public void setup() {

        this.request = new UserRequest("Eduardo", "eduardo.test@example.com", "senha123", "41230752072", "9938658560");
        this.userResponse = new UserResponse(1L, "Eduardo", "eduardo.test@example.com", "41230752072", "9938658560", "TENANT", "senha123");
       this.tenant = new Tenant(this.userResponse);
        List<UserResponse> userResponsesList = List.of(this.userResponse);
        this.pageResponse = new PageImpl<>(userResponsesList);
        this.updatedUser = new UserResponse(1L, "Francisco", "francisco.test@example.com", "41230752045", "9938658561", "TENANT", "senhaForte123");
    }


    @Test
    @DisplayName("should return http status 200 and a user")
    @WithMockUser(roles = "TENANT")
    void first_scenario() throws Exception {

       when(service.findById(any())).thenReturn(this.userResponse);

       var response = mvc.perform(
               get("/tenant/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.userResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }


    @Test
    @DisplayName("should return http status 200 ok and the user updated")
    @WithMockUser(roles = "TENANT")
    void second_scenario() throws Exception {
       when(service.update(any(), any())).thenReturn(this.updatedUser);

       var response = mvc.perform(
               put("/tenant/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
                       .content(String.valueOf(reqTester.write(
                               this.request
                       ).getJson()))
       ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.updatedUser).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);

        verify(service).update(eq(1L), any(UserRequest.class));
    }

    @Test
    @DisplayName("should return status 403 user forbidden")
    void third_scenario() throws Exception {
       when(service.findById(any())).thenReturn(this.userResponse);

       var response = mvc.perform(
               get("/users/{id}",1L)
                       .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("should return http status 401 unauthorized with invalid token")
    void fourth_scenario() throws Exception {
        var response = mvc.perform(
                get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


}
