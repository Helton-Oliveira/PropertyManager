package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.database.repository.UserRepository;
import com.propertymanager.demo.domain.dtos.AuthenticationDto;
import com.propertymanager.demo.domain.dtos.UserRequest;
import com.propertymanager.demo.domain.service.UserService;
import com.propertymanager.demo.infra.security.JwtTokenData;
import com.propertymanager.demo.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var tokenJWT = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new JwtTokenData(tokenJWT));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid UserRequest req) {
        if(this.repository.findByEmail(req.getEmail()) != null) return ResponseEntity.badRequest().build();

        req.setPassword(new BCryptPasswordEncoder().encode(req.getPassword()));
        var newUser = userService.save(req);

        return ResponseEntity.ok(newUser);
    }
}
