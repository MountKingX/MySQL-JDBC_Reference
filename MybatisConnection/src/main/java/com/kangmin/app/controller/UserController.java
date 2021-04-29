package com.kangmin.app.controller;

import java.util.List;

import com.kangmin.app.mapper.UserMapper;
import com.kangmin.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userMapper.getAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userMapper.getOne(id);
    }

    @PostMapping("/users")
    public void save(final @RequestBody User user) {
        userMapper.insert(user);
    }

    @PutMapping(value = "/users")
    public void update(final User user) {
        userMapper.update(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(final @PathVariable("id") Long id) {
        userMapper.delete(id);
    }
}
