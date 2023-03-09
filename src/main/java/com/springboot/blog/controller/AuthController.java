package com.springboot.blog.controller;


import com.springboot.blog.payload.JwtAuthResponseDTO;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.SignUpDTO;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<JwtAuthResponseDTO> login(@RequestBody LoginDTO loginDTO){

        String token= authService.login(loginDTO);
        JwtAuthResponseDTO jwtAuthResponseDTO=new JwtAuthResponseDTO();
        jwtAuthResponseDTO.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponseDTO);

    }
    @PostMapping("/signup")
    public ResponseEntity<String>signUp(@RequestBody SignUpDTO signUpDTO){
    String response= authService.signUp(signUpDTO);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
