package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.SignUpDTO;

public interface AuthService {


    String login(LoginDTO loginDTO);

    String signUp(SignUpDTO signUpDTO);

}
