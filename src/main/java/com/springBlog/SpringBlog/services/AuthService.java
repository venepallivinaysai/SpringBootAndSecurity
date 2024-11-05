package com.springBlog.SpringBlog.services;

import com.springBlog.SpringBlog.dao.RoleRepository;
import com.springBlog.SpringBlog.dao.UserRepository;
import com.springBlog.SpringBlog.dtos.LoginDto;
import com.springBlog.SpringBlog.dtos.RegisterDto;
import com.springBlog.SpringBlog.entities.Role;
import com.springBlog.SpringBlog.entities.User;
import com.springBlog.SpringBlog.exceptions.BlogApiException;
import com.springBlog.SpringBlog.exceptions.ResourseNotFoundException;
import com.springBlog.SpringBlog.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    // for registration
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterDto registerDto) throws Exception {
        // check for email exists in DB
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new Exception("User already Exists with provided email!");
        }
        // check for username exists in DB
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw  new BlogApiException("User already Exists with provided UserName!", HttpStatus.BAD_REQUEST);
        }

        User user= new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());

        Set<Role> roles= new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").get());

        user.setRoles(roles);

        userRepository.save(user);

        return  "User Created Successfully";
    }
}
