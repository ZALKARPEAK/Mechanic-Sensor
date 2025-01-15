package com.xemyru.sensorweather.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String extractName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
        final Claims claims = extractAllClaims(token);
        return claimsResolve.apply(claims);
    }
    public String generateToken(String sensorName) {
        return generateToken(new HashMap<>(), sensorName);
    }

    public String generateToken(
            Map<String, Object> extractClaim,
            String sensorName
    ) {
        return Jwts.builder().claims(extractClaim).subject(sensorName).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Boolean isTokenValid(String token, String sensorName) {
        final String sensor = extractName(token);

        if (isTokenExpired(token))
            throw new MalformedJwtException("JWT Token is expired!");

        return (sensor.equals(sensorName)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new MalformedJwtException("JWT Token is not valid!");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}