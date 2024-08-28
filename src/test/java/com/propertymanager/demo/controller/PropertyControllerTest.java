package com.propertymanager.demo.controller;

import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.dtos.AddressDto;
import com.propertymanager.demo.domain.dtos.PropertyRequest;
import com.propertymanager.demo.domain.dtos.PropertyResponse;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.PropertyService;
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
public class PropertyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PropertyResponse> resTester;

    @Autowired
    private JacksonTester<PropertyRequest> reqTester;

    @Autowired
    private JacksonTester<Page<PropertyResponse>> pageUserResponseTester;

    @MockBean
    private PropertyService service;

    private PropertyResponse propertyResponse;
    private PropertyResponse updatedProperty;
    private Page<PropertyResponse> pageResponse;
    private final Addres address = new Addres("Avenida das Palmeiras", "Jardim Primavera", "87654321", "456", "Rio de Janeiro", "RJ");;
    private final TypeProperty typeProperty = TypeProperty.valueOf("APARTAMENT");
    private final UserResponse userResponse = new UserResponse(2L, "Carlos", "carlos@test.com", "41230752072", "9938658560", "OWNER", "senhaForte1234");
    private final PropertyRequest request = new PropertyRequest(this.typeProperty, "700", "the best apartment", 1L, new AddressDto(this.address));;
    private final Property property = new Property(this.request, this.userResponse);

   @BeforeEach
     public void setup() {
        this.property.setId(1L);
        this.propertyResponse = new PropertyResponse(this.property, this.request);
        List<PropertyResponse> propertyResponsesList = List.of(this.propertyResponse);
        this.pageResponse = new PageImpl<>(propertyResponsesList);
        this.request.setRentalValue("1200");
        this.updatedProperty = new PropertyResponse(this.property,this.request);
    }

    @Test
    @DisplayName("It should return status 400 if any value is wrong or null")
    @WithMockUser(roles = "ADMIN")
    void first_scenario() throws Exception {
        var response = mvc.perform(post(("/property")))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http status 201 created")
    @WithMockUser(roles = "ADMIN")
    void second_scenario() throws Exception {

       when(service.save(any())).thenReturn(this.propertyResponse);

        var response = mvc
                .perform(
                        post("/property")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(reqTester.write(
                                        this.request
                                ).getJson()))
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = resTester.write(this.propertyResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("It should return http status 200 and a list of users")
    @WithMockUser(roles = "ADMIN")
    void third_scenario() throws Exception {

       when(service.findAll(any(Pageable.class))).thenReturn(pageResponse);

       var response = mvc.perform(
               get("/property")
                       .param("page", "0")
                       .param("size", "1")
                       .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       var expectedList = pageUserResponseTester.write(this.pageResponse).getJson();

       assertThat(response.getContentAsString()).isEqualTo(expectedList);

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("should return http status 200 and a user")
    @WithMockUser(roles = "ADMIN")
    void fourth_scenario() throws Exception {

       when(service.findById(any())).thenReturn(this.propertyResponse);

       var response = mvc.perform(
               get("/property/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
       ).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.propertyResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("should return an http status 204 and have no content")
    @WithMockUser(roles = "ADMIN")
    void fifth_scenario() throws Exception {

      when(service.delete(any(Long.class))).thenReturn(true);

      var response = mvc.perform(
              delete("/property/{id}", 1L)
                      .contentType(MediaType.APPLICATION_JSON)
      ).andReturn().getResponse();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
      assertThat(response.getContentAsString()).isEmpty();

    }

    @Test
    @DisplayName("should return http status 200 ok and the user updated")
    @WithMockUser(roles = "ADMIN")
    void sixth_scenario() throws Exception {
       when(service.update(any(), any())).thenReturn(this.updatedProperty);

       var response = mvc.perform(
               put("/property/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
                       .content(String.valueOf(reqTester.write(
                               this.request
                       ).getJson()))
       ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.updatedProperty).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);

        verify(service).update(eq(1L), any(PropertyRequest.class));
    }

}
