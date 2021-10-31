package com.buenoezandro.api.services.impl;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.repositories.UserRepository;
import com.buenoezandro.api.services.exceptions.EmailAlreadyExistsException;
import com.buenoezandro.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    private static final Integer ID                  = 1;
    private static final Integer INDEX               = 0;
    private static final String NAME                 = "Ezandro";
    private static final String EMAIL                = "ezandro@teste.com";
    private static final String PASSWORD             = "123";
    private static final String USER_NOT_FOUND       = "Usuário não encontrado!";
    private static final String EMAIL_ALREADY_EXISTS = "E-mail já cadastrado no sistema!";

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
    void whenFindAllThenReturnAListOfUsers() {
        when(this.userRepository.findAll()).thenReturn(List.of(this.user));

        List<User> users = this.userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(User.class, users.get(INDEX).getClass());
        assertEquals(ID, users.get(INDEX).getId());
        assertEquals(NAME, users.get(INDEX).getName());
        assertEquals(ID, users.get(INDEX).getId());
        assertEquals(ID, users.get(INDEX).getId());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(this.userRepository.save(any())).thenReturn(this.user);

        var user = this.userService.create(this.userDTO);

        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    void whenCreateThenThrowAnEmailAlreadyExistsException() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(this.optionalUser);

        try {
            if (this.optionalUser.isPresent()) {
                this.optionalUser.get().setId(2);
                this.userService.create(this.userDTO);
            }
        } catch (Exception e) {
            assertEquals(EmailAlreadyExistsException.class, e.getClass());
            assertEquals(EMAIL_ALREADY_EXISTS, e.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(this.userRepository.save(any())).thenReturn(this.user);

        var user = this.userService.update(ID, this.userDTO);

        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    void whenUpdateThenThrowAnEmailAlreadyExistsException() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(this.optionalUser);

        try {
            if (this.optionalUser.isPresent()) {
                this.optionalUser.get().setId(2);
                this.userService.update(ID, this.userDTO);
            }
        } catch (Exception e) {
            assertEquals(EmailAlreadyExistsException.class, e.getClass());
            assertEquals(EMAIL_ALREADY_EXISTS, e.getMessage());
        }
    }

    @Test
    void whenValidUserIdIsGivenThenItShouldBeDeleted() {
        when(this.userRepository.findById(anyInt())).thenReturn(this.optionalUser);
        doNothing().when(this.userRepository).deleteById(anyInt());
        this.userService.delete(ID);
        verify(this.userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenInvalidUserIdIsGivenThenAnExceptionShouldBeThrown() {
        when(this.userRepository.findById(anyInt())).thenThrow(new UserNotFoundException(USER_NOT_FOUND));

       try {
           this.userService.delete(ID);
       } catch (Exception e) {
            assertEquals(UserNotFoundException.class, e.getClass());
            assertEquals(USER_NOT_FOUND, e.getMessage());
       }
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}