package com.sportsmatch.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public String extractUserName(String token) {
        return null;
    }
}
