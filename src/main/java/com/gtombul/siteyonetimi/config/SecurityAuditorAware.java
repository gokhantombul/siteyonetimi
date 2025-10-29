package com.gtombul.siteyonetimi.config;

import com.gtombul.siteyonetimi.model.user.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; // <-- YENİ EKLENEN ANOTASYON

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Eğer güvenlik bağlamı yoksa, anonimse veya doğrulanmamışsa
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            // BaseEntityListener'daki gibi '1L' (Sistem) kullanıcısını varsayılan yapabiliriz.
            // Veya loglamalar için 'null' dönmek daha doğru olabilir.
            // Proje tutarlılığı için 1L (Sistem/Admin) varsayalım.
            return Optional.of(1L);
        }

        // Güvenlik bağlamından principal'ı al
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Optional.of(userPrincipal.getId());
    }

    /**
     * Bu, AuditorAware arayüzünün bir parçası DEĞİLDİR, ancak
     * TalepServiceImpl'de ihtiyaç duyduğumuz 'UserEntity' nesnesini
     * doğrudan almak için eklediğimiz özel bir yardımcı metottur.
     * * NOT: Bu metot burada olmamalı. Servis, sadece ID'yi almalı
     * ve UserRepository'yi kullanarak Entity'yi kendisi çekmelidir.
     * * Lütfen TalepServiceImpl'deki 'aktifKullaniciyiGetir' metodunun
     * mevcut halini (bir önceki cevaptaki) KORUYUN. O metot,
     * bu bean'i enjekte edip getCurrentAuditor() metodunu çağırır.
     */
}