package com.java.SSJ.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

 @Value("${jwt.secret}")
 private String jwtSecret;

 @Value("${jwt.expiration}")
 private int jwtExpirationInMs;

 public String generateToken(UserDetails userDetails) {
     Map<String, Object> claims = new HashMap<>();
     return Jwts.builder()
             .setClaims(claims)
             .setSubject(userDetails.getUsername())
             .setIssuedAt(new Date())
             .setExpiration(new Date(new Date().getTime() + jwtExpirationInMs))
             .signWith(SignatureAlgorithm.HS512, jwtSecret)
             .compact();
 }

 public String getUsernameFromToken(String token) {
     Claims claims = Jwts.parser()
             .setSigningKey(jwtSecret)
             .parseClaimsJws(token)
             .getBody();
     return claims.getSubject();
 }

 public boolean validateToken(String token, UserDetails userDetails) {
     String username = getUsernameFromToken(token);
     return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
 }

 private boolean isTokenExpired(String token) {
     Date expirationDate = Jwts.parser()
             .setSigningKey(jwtSecret)
             .parseClaimsJws(token)
             .getBody()
             .getExpiration();
     return expirationDate.before(new Date());
 }
}
