package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncoder {

    // To Generate Encrypted Passwords

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("passw"));
        System.out.println(passwordEncoder.encode("admin"));

    }
}
