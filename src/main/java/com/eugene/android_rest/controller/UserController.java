package com.eugene.android_rest.controller;

import com.eugene.android_rest.database.services.interfaces.UserService;
import com.eugene.android_rest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.update(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }
}
