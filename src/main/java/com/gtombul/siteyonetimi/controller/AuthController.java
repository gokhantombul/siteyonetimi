package com.gtombul.siteyonetimi.controller;

import com.gtombul.siteyonetimi.config.CustomUserDetailsService;
import com.gtombul.siteyonetimi.dto.AuthRequest;
import com.gtombul.siteyonetimi.dto.AuthResponse;
import com.gtombul.siteyonetimi.dto.RegisterRequest;
import com.gtombul.siteyonetimi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        String token = jwtTokenProvider.createToken(authRequest.username(), "USER");
        String refreshToken = jwtTokenProvider.createToken(authRequest.username(), "REFRESH");

        return ResponseEntity.ok(new AuthResponse(token, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        userDetailsService.saveUser(registerRequest);
        return ResponseEntity.ok(Map.of("message", "Kayıt başarılı"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);
            String token = jwtTokenProvider.createToken(username, "USER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Geçersiz refresh token"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            jwtTokenProvider.blacklistToken(token);
            return ResponseEntity.ok(Map.of("message", "Logout başarılı"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Geçersiz token"));
    }

}
