package com.buenoezandro.api.resources;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserResource {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(this.userService.findById(id));
    }
}
