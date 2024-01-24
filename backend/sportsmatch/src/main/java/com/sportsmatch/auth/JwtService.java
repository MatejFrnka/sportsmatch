package com.sportsmatch.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // Takes Single Claim from JWT Token
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) { // Takes all the claims from JWT Token
    return Jwts.parser()
        .verifyWith(getVerificationKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getVerificationKey() { // Sign JWT Token base from secret
    byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
