package com.gtombul.siteyonetimi.dto.duyuru;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * Yeni bir duyuru oluşturmak için API'den alınacak JSON verisini temsil eder.
 * Dosyalar bu DTO'nun içinde değil, @RequestPart ile ayrı alınır.
 */
@Data
public class DuyuruCreateRequest {

    @NotBlank(message = "Duyuru başlığı boş bırakılamaz.")
    @Size(min = 3, max = 200)
    private String baslik;

    @NotBlank(message = "Duyuru içeriği boş bırakılamaz.")
    private String icerik;

    @NotNull(message = "Yayımlanma tarihi boş bırakılamaz.")
    private LocalDate yayinlanmaTarihi;

    @FutureOrPresent(message = "Geçerlilik tarihi geçmiş bir tarih olamaz.")
    private LocalDate gecerlilikTarihi;
}