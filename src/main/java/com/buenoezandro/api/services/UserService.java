package com.buenoezandro.api.services;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO userDTO);
    User update(Integer id, UserDTO userDTO);
    void delete(Integer id);
}
