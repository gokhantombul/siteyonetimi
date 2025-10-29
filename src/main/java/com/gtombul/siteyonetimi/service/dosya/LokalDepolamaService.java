package com.gtombul.siteyonetimi.service.dosya.impl;

import com.gtombul.siteyonetimi.model.dosya.DepolamaTipi;
import com.gtombul.siteyonetimi.service.dosya.DepolamaService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class LokalDepolamaService implements DepolamaService {

    @Value("${dosya.depolama.lokal.kok-dizin:./uploads}") // application.properties'den okunacak
    private String kokDizin;

    @Value("${dosya.depolama.public-url-prefix:http://localhost:8080/api/v1/dosyalar/public}")
    private String publicUrlPrefix;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(kokDizin));
            log.info("Lokal dosya deposu başlatıldı: {}", kokDizin);
        } catch (IOException e) {
            log.error("Lokal dosya deposu oluşturulamadı!", e);
            throw new RuntimeException("Depolama alanı oluşturulamadı.", e);
        }
    }

    @Override
    public DepolamaTipi getDepolamaTipi() {
        return DepolamaTipi.LOCAL;
    }

    @Override
    public String kaydet(MultipartFile dosya, String dosyaYolu, String benzersizAdi) {
        try {
            Path hedefDizin = Paths.get(this.kokDizin, dosyaYolu);
            Files.createDirectories(hedefDizin); // Gerekirse alt dizinleri oluştur

            Path hedefDosya = hedefDizin.resolve(benzersizAdi);

            try (InputStream inputStream = dosya.getInputStream()) {
                Files.copy(inputStream, hedefDosya, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("'{}' dosyası başarıyla '{}' konumuna kaydedildi.", dosya.getOriginalFilename(), hedefDosya);
            return hedefDosya.toString();
        } catch (IOException e) {
            log.error("'{}' dosyası kaydedilirken hata oluştu.", dosya.getOriginalFilename(), e);
            throw new RuntimeException("Dosya kaydedilemedi.", e);
        }
    }

    @Override
    public Resource yukle(String dosyaYolu, String benzersizAdi) {
        try {
            Path dosya = Paths.get(kokDizin, dosyaYolu, benzersizAdi);
            Resource resource = new UrlResource(dosya.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Dosya okunamadı: " + benzersizAdi);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL hatası: " + benzersizAdi, e);
        }
    }

    /**
     * Belirtilen konumdaki fiziksel dosyayı diskten siler.
     */
    @Override
    public void sil(String dosyaYolu, String benzersizAdi) {
        try {
            Path hedefDosya = Paths.get(kokDizin, dosyaYolu, benzersizAdi);
            if (Files.exists(hedefDosya)) {
                Files.delete(hedefDosya);
                log.info("'{}' konumundaki dosya başarıyla silindi.", hedefDosya);
            } else {
                log.warn("'{}' konumunda silinecek dosya bulunamadı, işlem atlanıyor.", hedefDosya);
            }
        } catch (IOException e) {
            log.error("'{}' konumundaki dosya silinirken bir hata oluştu.", Paths.get(kokDizin, dosyaYolu, benzersizAdi), e);
            // Burada hatayı yutmak yerine yukarıya fırlatmak, @Transactional'ın
            // veritabanı işlemini geri almasını sağlar.
            throw new RuntimeException("Fiziksel dosya silinemedi.", e);
        }
    }

    // ... sil() ve publicUrlAl() metotları ...
    @Override
    public String publicUrlAl(String dosyaYolu, String benzersizAdi) {
        return String.format("%s/%s/%s", publicUrlPrefix, dosyaYolu, benzersizAdi);
    }
}