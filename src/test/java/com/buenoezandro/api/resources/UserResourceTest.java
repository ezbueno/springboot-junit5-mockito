package com.buenoezandro.api.resources;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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
    void whenFindByIdThenReturnSuccess() {
        when(this.userService.findById(anyInt())).thenReturn(this.user);
        when(this.modelMapper.map(any(), any())).thenReturn(this.userDTO);

        ResponseEntity<UserDTO> response = this.userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
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