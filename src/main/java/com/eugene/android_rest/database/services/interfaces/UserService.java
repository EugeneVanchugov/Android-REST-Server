package com.eugene.android_rest.database.services.interfaces;

import com.eugene.android_rest.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
}
