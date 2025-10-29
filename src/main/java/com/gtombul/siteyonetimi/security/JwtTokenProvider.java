package com.gtombul.siteyonetimi.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    private final long accessTokenValidityMs = 3600000;  // 1 saat
    private final long refreshTokenValidityMs = 604800000; // 7 gün

    // Blacklist örneği (memory tabanlı)
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String createToken(String username, String role) {
        return generateToken(username, role, accessTokenValidityMs);
    }

    public String createRefreshToken(String username) {
        return generateToken(username, "REFRESH", refreshTokenValidityMs);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    private String generateToken(String username, String role, long duration) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + duration);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            if (blacklistedTokens.contains(token)) return false;
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public String getUsername(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }

    public String getRole(String token) {
        return (String) jwtParser.parseSignedClaims(token).getPayload().get("role");
    }

    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
