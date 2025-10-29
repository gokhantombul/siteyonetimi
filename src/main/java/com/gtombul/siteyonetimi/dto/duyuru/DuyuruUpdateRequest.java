package com.gtombul.siteyonetimi.dto.duyuru;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Mevcut bir duyuruyu güncellemek için kullanılır.
 */
@Data
public class DuyuruUpdateRequest {

    @NotBlank
    @Size(min = 3, max = 200)
    private String baslik;

    @NotBlank
    private String icerik;

    @NotNull
    private LocalDate yayinlanmaTarihi;

    @FutureOrPresent
    private LocalDate gecerlilikTarihi;

    /**
     * Güncelleme sırasında hangi mevcut dosyaların silineceğini belirtir.
     * Yeni dosyalar yine @RequestPart ile ayrı yüklenir.
     */
    private Set<UUID> silinecekDosyaUuidleri;
}