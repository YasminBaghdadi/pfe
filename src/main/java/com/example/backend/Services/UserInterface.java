package com.example.backend.Services;


import com.example.backend.Entity.User;

import java.util.List;

public interface UserInterface {
    User addUser(User user);
    void deleteUser(Long idUser);
    public String addUserWTCP(User user);
    public String addUserWTUN(User user);
    public User updateUser(Long idUser ,User user);
    List<User> getAllusers(int page, int size);
    public User getUser(Long idUser);
    public User getUserByUsername(String username);
}
