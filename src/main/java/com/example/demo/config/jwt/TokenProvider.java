package com.example.demo.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long userId) {
        Date now = new Date();
        return makeAccessToken(new Date(now.getTime() + jwtProperties.getAccessExpirationMs()), userId);
    }

    private String makeAccessToken(Date expiry, Long userId) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("id", userId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getAccessPrivate())   // ES256의 경우 Base64-encoded key bytes may only be specified for HMAC signatures. 오류 발생
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        String uuid = UUID.randomUUID().toString();
        return makeRefreshToken(new Date(now.getTime() + jwtProperties.getRefreshExpirationMs()), userId, uuid);
    }

    private String makeRefreshToken(Date expiry, Long userId, String uuid) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("id", userId)
                .claim("jti", uuid)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshPrivate())
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getAccessPrivate())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Claims getJtiFromRefreshJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getRefreshPrivate())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public Long getUserIdFromAccessJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getAccessPrivate())
                .build()
                .parseClaimsJws(token)
                .getPayload().get("id",Long.class);
    }



}
