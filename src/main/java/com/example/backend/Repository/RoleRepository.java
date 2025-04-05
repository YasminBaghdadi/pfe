package com.example.backend.Repository;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.Rolename;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(Rolename rolename);

}
