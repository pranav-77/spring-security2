package com.spring.securityDemo.security;

import com.spring.securityDemo.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Creating JWT Token
@Service
public class JwtService {

    @Value("${security.secretKey}")
    private String key;

    public String createToken(Employee employee) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", employee.getName());
        map.put("email", employee.getEmail());

        return Jwts
                .builder()
                .claims()
                .add(map)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+600000))
                .subject(employee.getId()+"")
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractName(String token) {
        Claims claims = extractToken(token);
        return (String) claims.get("name");
    }

    private Claims extractToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public boolean extractExpiry(String token) {
        return extractToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }
}
