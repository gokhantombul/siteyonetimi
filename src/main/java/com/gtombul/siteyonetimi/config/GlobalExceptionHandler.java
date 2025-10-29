package com.gtombul.siteyonetimi.config;

import com.gtombul.siteyonetimi.dto.hata.HataYanitiDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HataYanitiDto> handleNotFound(EntityNotFoundException ex) {
        log.warn("Kaynak bulunamadı: {}", ex.getMessage());
        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.NOT_FOUND.value())
                .hataKodu("KAYNAK_BULUNAMADI")
                .mesaj(ex.getMessage())
                .build();
        return new ResponseEntity<>(hataYaniti, HttpStatus.NOT_FOUND);
    }

    // 400 - Bad Request (Validation Errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HataYanitiDto> handleValidation(MethodArgumentNotValidException ex) {
        var hatalar = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));

        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.BAD_REQUEST.value())
                .hataKodu("VALIDASYON_HATASI")
                .mesaj("Girilen verilerde validasyon hataları bulunmaktadır.")
                .detaylar(hatalar) // Hataları detaylar map'ine atıyoruz
                .build();

        log.warn("Validasyon hatası: {}", hatalar);
        return new ResponseEntity<>(hataYaniti, HttpStatus.BAD_REQUEST);
    }

    // 400 - Bad Request (Generic)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HataYanitiDto> handleIllegalArg(IllegalArgumentException ex) {
        log.warn("Geçersiz istek argümanı: {}", ex.getMessage());
        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.BAD_REQUEST.value())
                .hataKodu("GECERSIZ_ISTEK")
                .mesaj(ex.getMessage())
                .build();
        return new ResponseEntity<>(hataYaniti, HttpStatus.BAD_REQUEST);
    }

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HataYanitiDto> handleAccessDenied(AccessDeniedException ex) {
        log.error("Yetkisiz erişim denemesi yapıldı.", ex);
        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.FORBIDDEN.value())
                .hataKodu("YETKISIZ_ERISIM")
                .mesaj("Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır.")
                .build();
        return new ResponseEntity<>(hataYaniti, HttpStatus.FORBIDDEN);
    }

    // 409 - Conflict (Örn: unique constraint ihlali)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HataYanitiDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Veritabanı bütünlüğü hatası oluştu.", ex);
        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.CONFLICT.value())
                .hataKodu("VERI_BUTUNLUGU_IHLALI")
                .mesaj("İşlem, veritabanı kısıtlamalarını ihlal etti. (Örn: Mükerrer kayıt)")
                .build();
        return new ResponseEntity<>(hataYaniti, HttpStatus.CONFLICT);
    }

    // 500 - Internal Server Error (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HataYanitiDto> handleAllExceptions(Exception ex) {
        // En kritik loglama burasıdır. Beklenmedik tüm hatalar buraya düşer.
        log.error("Beklenmedik bir sunucu hatası oluştu.", ex);

        HataYanitiDto hataYaniti = HataYanitiDto.builder()
                .httpDurumu(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .hataKodu("BEKLENMEDIK_HATA")
                .mesaj("Sunucuda beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyin.")
                .build();

        return new ResponseEntity<>(hataYaniti, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}