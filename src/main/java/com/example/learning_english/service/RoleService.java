package com.example.learning_english.service;

import com.example.learning_english.entity.Role;
import com.example.learning_english.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(Role role){
        roleRepository.save(role);
    }

    public void deleteAll(){
        roleRepository.deleteAll();
    }
}
