package com.tejesh.TaskMate.controller;

import com.tejesh.TaskMate.entity.Users;
import com.tejesh.TaskMate.payload.JwtAuthResponse;
import com.tejesh.TaskMate.payload.LoginDto;
import com.tejesh.TaskMate.payload.UserDto;
import com.tejesh.TaskMate.security.JwtTokenProvider;
import com.tejesh.TaskMate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taskmate/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        System.out.println("Generated token: " + token);

        return ResponseEntity.ok(new JwtAuthResponse(token));

    }
}
