package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;

public interface UserService {
    User getUserById(Long id);

    User updateUser(Long id, String email, String username, String password);
}
