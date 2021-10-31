package com.buenoezandro.api.resources;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

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
    void whenFindAllThenReturnAListOfUserDTO() {
        when(this.userService.findAll()).thenReturn(Stream.of(this.user).toList());
        when(this.modelMapper.map(any(), any())).thenReturn(this.userDTO);

        ResponseEntity<List<UserDTO>> response = this.userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Stream.of().toList().getClass(), response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(this.userService.create(any())).thenReturn(this.user);

        ResponseEntity<UserDTO> response = this.userResource.create(this.userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(this.userService.update(ID, this.userDTO)).thenReturn(this.user);
        when(this.modelMapper.map(any(), any())).thenReturn(this.userDTO);

        ResponseEntity<UserDTO> response = this.userResource.update(ID, this.userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void delete() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}