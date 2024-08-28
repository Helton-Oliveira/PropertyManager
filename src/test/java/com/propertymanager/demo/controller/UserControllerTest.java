package com.propertymanager.demo.controller;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.UserService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserResponse> resTester;

    @Autowired
    private JacksonTester<UserRequest> reqTester;

    @Autowired
    private JacksonTester<Page<UserResponse>> pageUserResponseTester;

    @MockBean
    private UserService service;

    private User user;
    private UserRequest request;
    private UserResponse userResponse;
    private Page<UserResponse> pageResponse;
    private UserResponse updatedUser;

   @BeforeEach
     public void setup() {
        this.user = new User(1L, "Eduardo", "eduardo.test@example.com", "senha123", "41230752072", "9938658560", true, "ADMIN");
        this.request = new UserRequest("Eduardo", "eduardo.test@example.com", "senha123", "41230752072", "9938658560");
        this.userResponse = new UserResponse(this.user);
        List<UserResponse> userResponsesList = List.of(this.userResponse);
        this.pageResponse = new PageImpl<>(userResponsesList);
        this.updatedUser = new UserResponse(1L, "Francisco", "francisco.test@example.com", "41230752045", "9938658561", "ADMIN", "senhaForte123");
    }

    @Test
    @DisplayName("It should return status 400 if any value is wrong or null")
    @WithMockUser(roles = "ADMIN")
    void first_scenario() throws Exception {
        var response = mvc.perform(post(("/users")))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http status 201 created")
    @WithMockUser(roles = "ADMIN")
    void second_scenario() throws Exception {

       when(service.save(any())).thenReturn(this.userResponse);

        var response = mvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(reqTester.write(
                                        this.request
                                ).getJson()))
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = resTester.write(this.userResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("It should return http status 200 and a list of users")
    @WithMockUser(roles = "ADMIN")
    void third_scenario() throws Exception {

       when(service.findAll(any(Pageable.class))).thenReturn(pageResponse);

       var response = mvc.perform(
               get("/users")
                       .param("page", "0")
                       .param("size", "1")
                       .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

        System.out.println("resposta: " + response.getContentType());

       var expectedList = pageUserResponseTester.write(this.pageResponse).getJson();

       assertThat(response.getContentAsString()).isEqualTo(expectedList);

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("should return http status 200 and a user")
    @WithMockUser(roles = "ADMIN")
    void fourth_scenario() throws Exception {

       when(service.findById(any())).thenReturn(this.userResponse);

       var response = mvc.perform(
               get("/users/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.userResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("should return an http status 204 and have no content")
    @WithMockUser(roles = "ADMIN")
    void fifth_scenario() throws Exception {

      when(service.delete(any(Long.class))).thenReturn(true);

      var response = mvc.perform(
              delete("/users/{id}", 1L)
                      .contentType(MediaType.APPLICATION_JSON)
      ).andReturn().getResponse();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
      assertThat(response.getContentAsString()).isEmpty();

    }

    @Test
    @DisplayName("should return http status 200 ok and the user updated")
    @WithMockUser(roles = "ADMIN")
    void sixth_scenario() throws Exception {
       when(service.update(any(), any())).thenReturn(this.updatedUser);

       var response = mvc.perform(
               put("/users/{id}", 1L)
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
    @WithMockUser()
    void seventh_scenario() throws Exception {
       when(service.findById(any())).thenReturn(this.userResponse);

       var response = mvc.perform(
               get("/users/{id}",1L)
                       .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("should return http status 401 unauthorized with invalid token")
    void eighth_scenario() throws Exception {
        var response = mvc.perform(
                get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


}
