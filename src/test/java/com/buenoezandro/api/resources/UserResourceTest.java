package com.buenoezandro.api.resources;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID      = 1;
    private static final Integer INDEX   = 0;
    private static final String NAME     = "Ezandro";
    private static final String EMAIL    = "ezandro@teste.com";
    private static final String PASSWORD = "123";

    private UserResource userResource;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        this.userResource = new UserResource(this.userService, this.modelMapper);
        this.startUser();
    }

    @Test
    void findById() {
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
    }
}