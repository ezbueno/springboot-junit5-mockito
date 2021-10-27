package com.buenoezandro.api.services.impl;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.repositories.UserRepository;
import com.buenoezandro.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        var user = this.userRepository.findById(id);
        return user.orElse(null);
    }
}
