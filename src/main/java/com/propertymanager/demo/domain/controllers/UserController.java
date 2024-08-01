package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 10, sort = {"name"}) Pageable page) {
        var users = userService.fetchAllUsers(page);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserByid(@PathVariable Long id) {
        var user = userService.getUserDetailsById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    @Transactional()
    public ResponseEntity addUser(@RequestBody @Valid UserRequest req, UriComponentsBuilder uriBuild) {
        var userCreated = userService.createUserAccount(req);
        var uri = uriBuild.path("/admin/{id}").buildAndExpand(userCreated.id()).toUri();

        return ResponseEntity.created(uri).body(userCreated);
    }

    @PostMapping("/tenants")
    @Transactional()
    public ResponseEntity addTenant(@RequestBody @Valid UserRequest req, UriComponentsBuilder uriBuild) {
        var userCreated = userService.createTenantAccount(req);
        var uri = uriBuild.path("/admin/createT/{id}").buildAndExpand(userCreated.id()).toUri();

        return ResponseEntity.created(uri).body(userCreated);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUserDetails(@RequestBody UserRequest req, @PathVariable Long id) {
        var response = userService.updateUserAccount(req, id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removeUser(@PathVariable Long id) {
        userService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }

}
