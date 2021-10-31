package com.buenoezandro.api.resources.exceptions;

import com.buenoezandro.api.services.exceptions.EmailAlreadyExistsException;
import com.buenoezandro.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    private ResourceExceptionHandler exceptionHandler;

    private static final String USER_NOT_FOUND       = "Usuário não encontrado!";
    private static final String EMAIL_ALREADY_EXISTS = "E-mail já cadastrado no sistema!";

    @BeforeEach
    void setUp() {
        this.exceptionHandler = new ResourceExceptionHandler();
    }

    @Test
    void whenUserNotFoundExceptionThenNotFoundShouldBeReturned() {
        ResponseEntity<StandardError> response = this.exceptionHandler
                .userNotFound(new UserNotFoundException(USER_NOT_FOUND), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(USER_NOT_FOUND, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenEmailAlreadyExistsExceptionThenBadRequestShouldBeReturned() {
        ResponseEntity<StandardError> response = this.exceptionHandler
                .emailAlreadyExists(new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(EMAIL_ALREADY_EXISTS, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
        assertNotEquals("/user/2", response.getBody().getPath());
    }
}