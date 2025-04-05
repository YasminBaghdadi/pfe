package com.example.backend.ServicesImpl;

import com.example.backend.Entity.Role;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Services.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServices implements RoleInterface {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
