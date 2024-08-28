package com.propertymanager.demo.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.propertymanager.demo.domain.abstractModels.TypeProperty;
import com.propertymanager.demo.domain.address.Addres;
import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.database.entity.Owner;
import com.propertymanager.demo.domain.database.entity.Property;
import com.propertymanager.demo.domain.database.entity.Tenant;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.ContractService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class ContractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ContractResponse> resTester;

    @Autowired
    private JacksonTester<ContractRequest> reqTester;

    @Autowired
    private JacksonTester<Page<ContractResponse>> pageContractResponseTester;

    @MockBean
    private ContractService service;

    private final ContractRequest request = new ContractRequest(1L, 7L, 3);
    private final UserResponse userResponse = new UserResponse(1L, "Carlos", "carlos@test.com", "41230752072", "9938658560", "TENANT", "senhaForte1234");
    private final Tenant tenant = new Tenant(this.userResponse);
    private final Owner owner = new Owner(this.userResponse);
    private final Addres addres = new Addres("Avenida das Palmeiras", "Jardim Primavera", "87654321", "456", "Rio de Janeiro", "RJ");

    private final TypeProperty typeProperty = TypeProperty.valueOf("HOUSE");
    private Property property;

    private Contract contract;
    private ContractResponse contractResponse;
    private Page<ContractResponse> pageResponse;

    @BeforeEach
    public void setup() {
        this.owner.setId(3L);
        this.owner.setRole("OWNER");
        this.property = new Property(2L, this.addres, this.typeProperty, "2200", "best location", true, this.owner);
        this.request.setContractPeriod((2));
        this.contract = new Contract(this.property, this.tenant, this.request);
        this.contract.setId(1L);
        this.contractResponse = new ContractResponse(this.contract, this.request);
        List<ContractResponse> contractResponses = List.of(this.contractResponse);
        this.pageResponse = new PageImpl<>(contractResponses);
    }

    @Test
    @DisplayName("It should return status 400 if any value is wrong or null")
    @WithMockUser(roles = "ADMIN")
    void first_scenario() throws Exception {
        var response = mvc.perform(post(("/contract")))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http status 201 created")
    @WithMockUser(roles = "ADMIN")
    void second_scenario() throws Exception {

        String requestJson = """
        {
            "tenantId": 1,
            "propertyId": 7,
            "contractPeriod": 2
        }
        """;

        when(service.save(any())).thenReturn(this.contractResponse);

        var response = mvc
                .perform(
                        post("/contract")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                ).andReturn().getResponse();

        System.out.println(response.getContentAsString());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = resTester.write(this.contractResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
        verify(service).save(any());
    }

    @Test
    @DisplayName("It should return http status 200 and a list of users")
    @WithMockUser(roles = "ADMIN")
    void third_scenario() throws Exception {

        when(service.findAll(any(Pageable.class))).thenReturn(pageResponse);

        var response = mvc.perform(
                get("/contract")
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        var expectedList = pageContractResponseTester.write(this.pageResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedList);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("should return http status 200 and a user")
    @WithMockUser(roles = "ADMIN")
    void fourth_scenario() throws Exception {

        when(service.findById(any())).thenReturn(this.contractResponse);

        var response = mvc.perform(
                get("/contract/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = resTester.write(this.contractResponse).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("should return an http status 204 and have no content")
    @WithMockUser(roles = "ADMIN")
    void fifth_scenario() throws Exception {

        when(service.delete(any(Long.class))).thenReturn(true);

        var response = mvc.perform(
                delete("/contract/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEmpty();

    }

}
