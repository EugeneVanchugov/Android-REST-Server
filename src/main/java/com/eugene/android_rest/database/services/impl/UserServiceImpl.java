package com.eugene.android_rest.database.services.impl;

import com.eugene.android_rest.annotations.ElasticDelete;
import com.eugene.android_rest.annotations.ElasticUpdate;
import com.eugene.android_rest.database.services.interfaces.UserService;
import com.eugene.android_rest.domain.User;
import com.eugene.android_rest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getById(Long id) {
        return repository.findOne(id);
    }

    @Override
    @ElasticUpdate
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    @ElasticUpdate
    public User update(User user) {
        if (repository.exists(user.getId())) {
            return repository.save(user);
        }
        return null;
    }

    @Override
    @ElasticDelete(User.class)
    public void delete(Long id) {
        repository.delete(id);
    }
}
