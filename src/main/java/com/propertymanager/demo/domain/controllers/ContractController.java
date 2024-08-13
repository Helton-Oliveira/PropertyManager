package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.database.entity.Contract;
import com.propertymanager.demo.domain.dtos.ContractRequest;
import com.propertymanager.demo.domain.dtos.ContractResponse;
import com.propertymanager.demo.domain.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract")
public class ContractController extends Controller<Contract, Long, ContractResponse, ContractRequest>{

    @Autowired
    private ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.service = contractService;
    }

}
