package com.springBlog.SpringBlog.controllers;

import com.springBlog.SpringBlog.dtos.JWTAuthResponse;
import com.springBlog.SpringBlog.dtos.LoginDto;
import com.springBlog.SpringBlog.dtos.RegisterDto;
import com.springBlog.SpringBlog.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token= authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse= new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws Exception {
        String response= authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
