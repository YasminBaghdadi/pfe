package com.example.backend.ServicesImpl;


import com.example.backend.Entity.Role;
import com.example.backend.Entity.Rolename;
import com.example.backend.Entity.User;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices implements UserInterface {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    // add user //

    @Override
    public User addUser(User user) {
        Role defaultRole = roleRepository.findByRolename(Rolename.CLIENT).orElseThrow(() -> new RuntimeException("Role not found!"));
        user.setRole(defaultRole);
        return userRepository.save(user);
    }

    // delete user //

    @Override
    public void deleteUser(Long idUser)
    {
        userRepository.deleteById(idUser);
    }

    @Override
    public String addUserWTCP(User user) {
        String ch = "";
        Role defaultRole = roleRepository.findByRolename(Rolename.CLIENT).orElseThrow(() -> new RuntimeException("Role not found!"));
        user.setRole(defaultRole);
        try {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                userRepository.save(user);
                ch = "User added successfully";
            } else {
                ch = "Passwords do not match";
            }
        } catch (Exception e) {
            ch = "Error: " + e.getMessage();
            e.printStackTrace();
        }
        return ch;
    }

    // adduser with username //

    @Override
    public String addUserWTUN(User user) {
        Role defaultRole = roleRepository.findByRolename(Rolename.CLIENT).orElseThrow(() -> new RuntimeException("Role not found!"));
        user.setRole(defaultRole);
        String ch = "";
        if (userRepository.existsByUsername(user.getUsername())) {
            ch = " user already exists";
        } else {
            userRepository.save(user);
            ch = "user added !!";
        }
        return ch;
    }

    // update user //

    @Override
    public User updateUser(Long idUser, User user) {
        User usr = userRepository.findById(idUser).get();
        usr.setUsername(user.getUsername());
        usr.setPassword(user.getPassword());
        usr.setConfirmPassword(user.getConfirmPassword());
        usr.setEmail(user.getEmail());
        usr.setFirstname(user.getFirstname());
        usr.setLastname(user.getLastname());
        return userRepository.save(usr);
    }

    // get all users //

    @Override
    public List<User> getAllusers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.getContent();
    }
    // get user //

    @Override
    public User getUser(Long idUser) {

        return userRepository.findById(idUser).orElse(null);
    }

    //  get user by username //


    @Override
    public User getUserByUsername(String un) {

        return userRepository.findByUsername(un);
    }
}

