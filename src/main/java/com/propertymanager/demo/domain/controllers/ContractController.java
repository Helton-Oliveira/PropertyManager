package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import com.propertymanager.demo.domain.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contract")
public class ContractController extends Controller<Contract, Long, ContractResponse, ContractRequest>{

    @Autowired
    private ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.service = contractService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContractResponse>> searchForTenantOrProperty(Pageable page,@RequestParam Map<String, String> req) {
        var response = contractService.filterByEntity(req, page);
        return ResponseEntity.ok(response);
    }
}
