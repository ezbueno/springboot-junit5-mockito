package com.buenoezandro.api.services;

import com.buenoezandro.api.domain.User;

public interface UserService {

    User findById(Integer id);
}
