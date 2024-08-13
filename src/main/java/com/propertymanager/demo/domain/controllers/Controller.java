package com.propertymanager.demo.domain.controllers;

import com.propertymanager.demo.domain.service.ServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Map;

@RestController
public abstract class Controller<T, ID, R, M> {

    @Autowired
    protected ServiceImpl<T, ID, R, M> service;

    @GetMapping
    public ResponseEntity<Page<R>> getAll(@PageableDefault(size = 10) Pageable page) {
        var response = service.findAll(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable ID id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q")
    public ResponseEntity<Page<R>> searchByCriteria(@PageableDefault(size = 10) Pageable page, @RequestParam Map<String, String> req) {
        var response = service.findByCriteria(req, page);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid M req) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var response = service.save(req);
        if(response == null) {
            return ResponseEntity.badRequest().body("Erro ao criar propriedade!");
        }
        var id = response.getClass().getMethod("getId").invoke(response);
        System.out.println(id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateEntity(@RequestBody M req, @PathVariable ID id) {
        var response = service.update(id, req);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntity(@PathVariable ID id) {
        var isDeleted = service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
