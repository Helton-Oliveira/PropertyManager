package com.propertymanager.demo.businessRules.usersRules;

import com.propertymanager.demo.domain.database.entity.User;
import com.propertymanager.demo.domain.database.repository.UserRepository;
import com.propertymanager.demo.infra.exceptioins.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShouldNotBeDeletedIfItIsTheOnlyOne implements AdminRules{

    @Autowired
    UserRepository repository;

    @Override
    public void valid() {
        List<User> user = repository.findAll();

        if(user.size() == 1) {
            throw new ValidateException("ERRO! Deve existir pelo menos 1 Administrador no sistema.");
        }
    }
}
