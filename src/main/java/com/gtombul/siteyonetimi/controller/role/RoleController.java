package com.gtombul.siteyonetimi.controller.role;

import com.gtombul.siteyonetimi.config.CustomUserDetailsService;
import com.gtombul.siteyonetimi.model.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class RoleController {

    private final CustomUserDetailsService userDetailsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String role = body.get("role"); // örnek: ROLE_ADMIN
        userDetailsService.assignRole(username, role);
        return ResponseEntity.ok(Map.of("message", "Rol atandı"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userDetailsService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", "Kullanıcı silindi"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/{username}")
    public ResponseEntity<?> getUserRoles(@PathVariable String username) {
        UserEntity user = userDetailsService.getUserByUsername(username);
        return ResponseEntity.ok(Map.of("username", username, "roles", user.getRoles()));
    }

}
