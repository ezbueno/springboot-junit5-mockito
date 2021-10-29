package com.buenoezandro.api.services.impl;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.repositories.UserRepository;
import com.buenoezandro.api.services.UserService;
import com.buenoezandro.api.services.exceptions.EmailAlreadyExistsException;
import com.buenoezandro.api.services.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    @Override
    public User create(UserDTO userDTO) {
        this.findByEmailOrThrow(userDTO);
        return this.userRepository.save(this.modelMapper.map(userDTO, User.class));
    }

    @Transactional
    @Override
    public User update(Integer id, UserDTO userDTO) {
        userDTO.setId(id);
        this.findByEmailOrThrow(userDTO);
        return this.userRepository.save(this.modelMapper.map(userDTO, User.class));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        this.findById(id);
        this.userRepository.deleteById(id);
    }

    private void findByEmailOrThrow(UserDTO userDTO) {
        var user = this.userRepository.findByEmail(userDTO.getEmail());

        if (user.isPresent() && !user.get().getId().equals(userDTO.getId())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado no sistema!");
        }
    }
}
