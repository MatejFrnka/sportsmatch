package com.sportsmatch.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  /**
   * Extracts the username from the provided JWT token.
   *
   * @param token The JWT token from which to extract the username.
   * @return The username extracted from the token.
   */
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a single claim from the provided JWT token using the specified claims resolver
   * function.
   *
   * @param token The JWT token from which to extract the claim.
   * @param claimsResolver The function to resolve the desired claim from the Claims object.
   * @param <T> The type of the claim.
   * @return The extracted claim.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a JWT Token based on the provided UserDetails.
   *
   * @param userDetails The UserDetails for which to generate the JWT Token.
   * @return The generated JWT Token.
   */
  public String generateToken(
      UserDetails userDetails) { // Generates JWT Token with only userDetails
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a JWT Token with additional claims based on the provided extra claims and
   * UserDetails.
   *
   * @param extraClaims The extra claims to include in the JWT Token.
   * @param userDetails The UserDetails for which to generate the JWT Token.
   * @return The generated JWT Token.
   */
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(getVerificationKey(), Jwts.SIG.HS256)
        .compact();
  }

  /**
   * Validates the provided JWT token against the given UserDetails.
   *
   * @param token The JWT token to be validated.
   * @param userDetails The UserDetails against which to validate the token.
   * @return True if the token is valid for the provided UserDetails; false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Checks if the provided JWT token has expired.
   *
   * @param token The JWT token to check for expiration.
   * @return True if the token has expired; false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the provided JWT token.
   *
   * @param token The JWT token from which to extract the expiration date.
   * @return The expiration date extracted from the token.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all claims from the provided JWT token.
   *
   * @param token The JWT token from which to extract all claims.
   * @return The Claims object containing all the claims from the token.
   */
  private Claims extractAllClaims(String token) { // Takes all the claims from JWT Token
    return Jwts.parser()
        .verifyWith(getVerificationKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Retrieves the secret key used for verifying and signing JWT tokens.
   *
   * @return The SecretKey used for JWT token operations.
   */
  private SecretKey getVerificationKey() { // Sign JWT Token base from secret
    byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
