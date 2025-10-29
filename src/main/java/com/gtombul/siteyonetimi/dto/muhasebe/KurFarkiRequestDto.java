package com.gtombul.siteyonetimi.dto.muhasebe;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class KurFarkiRequestDto {

    /**
     * Hesap ID listesi (boşsa tüm TRY dışı KASA/BANKA)
     */
    private List<Long> hesapIdListe;

    @NotNull
    private LocalDate tarih; // değerleme tarihi

    private String aciklama;

}
