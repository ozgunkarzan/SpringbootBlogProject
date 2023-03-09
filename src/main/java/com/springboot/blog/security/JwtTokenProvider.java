package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {


    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;


    //Generating JWT token

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate =new Date();
        Date expirationDate=new Date(currentDate.getTime()+jwtExpirationDate);

        System.out.println(jwtSecret);

        String token= Jwts.builder().setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
          return  Jwts.parserBuilder().setSigningKey(key())
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .getSubject(); //username is the subject of claims
}

    private Key key(){

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


   // JWT Token Validation
    boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException ex){
            throw new BlogAPIException("Invalid JWT token",HttpStatus.BAD_REQUEST);
        }catch (ExpiredJwtException ex){
            throw new BlogAPIException("Expired JWT token",HttpStatus.BAD_REQUEST);
        }catch (UnsupportedJwtException ex){
            throw new BlogAPIException("Unsupported JWT token",HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException ex){
            throw new BlogAPIException("JWT claims String is empty",HttpStatus.BAD_REQUEST);
        }
    }

}
