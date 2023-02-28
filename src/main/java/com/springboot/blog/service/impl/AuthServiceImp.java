package com.springboot.blog.service.impl;

import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.SignUpDTO;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User Logged In Successfully";
    }

    @Override
    public String signUp(SignUpDTO signUpDTO) {

        // check for username duplicate
        if(userRepository.existsByUsername(signUpDTO.getUsername())) {

        throw new BlogAPIException("The username is not available", HttpStatus.BAD_REQUEST);
        }


        // check for e-mail duplicate
        if(userRepository.existsByEmail(signUpDTO.getEmail())) {

            throw new BlogAPIException("The email is already registered", HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
