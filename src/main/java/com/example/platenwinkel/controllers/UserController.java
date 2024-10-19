package com.example.platenwinkel.controllers;


import com.example.platenwinkel.dtos.input.AuthorityInputDto;
import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.models.Authority;
import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.AuthorityRepository;
import com.example.platenwinkel.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.BadRequestException;

import java.net.URI;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository=authorityRepository;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserOutputDto>> getUsers() {

        List<UserOutputDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") Long userid) {

        UserOutputDto optionalUser = userService.getUser(userid);


        return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping(value = "")
    public ResponseEntity<UserOutputDto> createKlant(@RequestBody UserInputDto dto) {;

        Long newUserId = userService.createUser(dto);
        if (dto.getAuthorities() != null && !dto.getAuthorities().isEmpty()) {
            for (Authority authority : dto.getAuthorities()) {
                userService.addAuthority(newUserId, authority.getAuthority());
            }
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUserId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> updateKlant(@PathVariable("username") Long userid, @RequestBody UserOutputDto dto) {

        userService.updateUser(userid, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteKlant(@PathVariable("username") Long userid) {
        userService.deleteUser(userid);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") Long userid, @RequestBody AuthorityInputDto fields) {
        try {

            userService.addAuthority(userid, fields.authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }


}