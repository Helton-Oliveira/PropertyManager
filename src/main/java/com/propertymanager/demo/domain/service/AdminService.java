package com.propertymanager.demo.domain.service;

import com.propertymanager.demo.domain.dtos.AdminRequest;
import com.propertymanager.demo.domain.dtos.AdminResponse;
import com.propertymanager.demo.domain.entity.Admin;
import com.propertymanager.demo.domain.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Page<AdminResponse> getAllAdmins(Pageable page) {
        return adminRepository.findAll(page).map(AdminResponse::new);
    }

    public AdminResponse getOneAdmin(Long id) {
        var admin = adminRepository.getReferenceById(id);
        return new AdminResponse(admin);
    }

    public AdminResponse created(AdminRequest data) {
        System.out.println(data);
        var admin = new Admin(data);
        adminRepository.save(admin);

        return new AdminResponse(admin);
    }
}
