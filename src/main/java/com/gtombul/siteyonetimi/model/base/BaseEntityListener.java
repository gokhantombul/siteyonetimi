package com.gtombul.siteyonetimi.model.base;

import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.user.CustomUserDetails;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntityListener {

    @PrePersist
    public void create(BaseEntity entity) {
        entity.setUuid(UUID.randomUUID());
        entity.setOlusturmaTarihi(LocalDateTime.now());
        if (entity.getDurum() == null) {
            entity.setDurum(Durum.AKTIF);
        }

        if (entity.getOlusturan() == null) {
            CustomUserDetails currentUser = getCurrentUser();
            if (currentUser == null) {
                entity.setOlusturan(1L);
            } else {
                entity.setOlusturan(currentUser.getId());
            }
        }
    }

    @PreUpdate
    public void update(BaseEntity entity) {
        entity.setGuncellemeTarihi(LocalDateTime.now());
        if (entity.getDurum() == null) {
            entity.setDurum(Durum.PASIF);
        }

        if (entity.getGuncelleyen() == null || entity.getGuncelleyen() != 0) {
            CustomUserDetails currentUser = getCurrentUser();
            if (currentUser == null) {
                entity.setGuncelleyen(1L);
            } else {
                entity.setGuncelleyen(currentUser.getId());
            }
        }
    }

    @PreRemove
    public void preRemove(BaseEntity entity) {
        CustomUserDetails currentUser = getCurrentUser();
        if (currentUser == null) {
            entity.setGuncelleyen(1L);
        } else {
            entity.setGuncelleyen(currentUser.getId());
        }
        entity.setDurum(Durum.SILINDI);
        throw new UnsupportedOperationException("Fiziksel silme desteklenmiyor. Soft delete kullanılıyor.");
    }

    private CustomUserDetails getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication != null && authentication.isAuthenticated()) {
                return authentication.getPrincipal() == null ? null : (CustomUserDetails) authentication.getPrincipal();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

}
