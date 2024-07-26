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
    public ResponseEntity<Page<UserResponse>> getAll(@PageableDefault(size = 10, sort = {"name"}) Pageable page) {
        var users = userService.getAllUsers(page);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable Long id) {
        var user = userService.getOneUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    @Transactional()
    public ResponseEntity createUser(@RequestBody @Valid UserRequest req, UriComponentsBuilder uriBuilde) {
        var userCreated = userService.created(req);
        var uri = uriBuilde.path("/admin/{id}").buildAndExpand(userCreated.id()).toUri();

        return ResponseEntity.created(uri).body(userCreated);
    }

}
