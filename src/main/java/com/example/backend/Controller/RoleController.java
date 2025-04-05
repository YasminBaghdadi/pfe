package com.example.backend.Controller;

import com.example.backend.Entity.Role;
import com.example.backend.Services.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Role")
public class RoleController {
    @Autowired
    private RoleInterface roleInterface;

    @PostMapping("/addrole")
    public Role addRole(@RequestBody Role role) {
        return roleInterface.addRole(role);
    }
}
