package com.buenoezandro.api.resources;

import com.buenoezandro.api.domain.dto.UserDTO;
import com.buenoezandro.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserResource {

    private static final String ID = "/{id}";
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping(path = ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(this.modelMapper.map(this.userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(this.userService.findAll()
                .stream().map(x -> this.modelMapper.map(x, UserDTO.class)).toList());
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        var user = this.userService.create(userDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok()
                .body(this.modelMapper.map(this.userService.update(id, userDTO), UserDTO.class));
    }

    @DeleteMapping(path = ID)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
