package com.buenoezandro.api.services;

import com.buenoezandro.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
}
