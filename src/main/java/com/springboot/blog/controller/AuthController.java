package com.springboot.blog.controller;


import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // Sign-in Rest Api

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity <String> login(@RequestBody LoginDTO loginDTO){

        String response= authService.login(loginDTO);
        return ResponseEntity.ok(response);

    }

}
