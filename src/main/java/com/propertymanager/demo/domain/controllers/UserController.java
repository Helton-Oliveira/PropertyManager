package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.dtos.UserResponse;
import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Map;

@RestController()
@RequestMapping("/users")
public class UserController extends Controller<User, Long, UserResponse, UserRequest> {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity deleteEntity(Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/q")
    public ResponseEntity<Page<UserResponse>> findUserByCriteria(@PageableDefault(size = 10) Pageable page, @RequestParam Map<String, String> req){
        var response = userService.findByCriteria(page, req);
        return ResponseEntity.ok(response);
    }
}
