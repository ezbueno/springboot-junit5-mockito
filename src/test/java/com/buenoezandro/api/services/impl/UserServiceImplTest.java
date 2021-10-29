package com.buenoezandro.api.services.impl;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.repositories.UserRepository;
import com.buenoezandro.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    private static final Integer ID            = 1;
    private static final String NAME           = "Ezandro";
    private static final String EMAIL          = "ezandro@teste.com";
    private static final String PASSWORD       = "123";
    private static final String USER_NOT_FOUND = "Usuário não encontrado!";

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImpl(this.userRepository, this.modelMapper);
        this.startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(this.userRepository.findById(anyInt())).thenReturn(this.optionalUser);

        var user = this.userService.findById(ID);

        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAUserNotFoundException() {
        when(this.userRepository.findById(anyInt())).thenThrow(new UserNotFoundException(USER_NOT_FOUND));

        try {
            this.userService.findById(ID);
        } catch (Exception e) {
            assertEquals(UserNotFoundException.class, e.getClass());
            assertEquals(USER_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}