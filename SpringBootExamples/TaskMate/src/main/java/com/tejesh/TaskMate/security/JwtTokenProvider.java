package com.tejesh.TaskMate.security;

import com.tejesh.TaskMate.excpetionHandling.APIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey"; // 64+ chars



    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        String token = Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .signWith(getKey(),SignatureAlgorithm.HS512).compact();

        return token;
    }

    private Key getKey() {

        byte[] keyBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {

        Claims claims =  Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();

        return claims.getSubject();

//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .build().parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new APIException("Token issue "+e.getMessage());
        }
    }

    }
