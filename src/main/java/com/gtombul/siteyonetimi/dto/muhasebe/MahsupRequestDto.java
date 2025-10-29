package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MahsupRequestDto {

    private String fisNo;                 // opsiyonel; boşsa "MHS-<timestamp>"

    private LocalDate tarih;              // boşsa today

    private String aciklama;

    @NotEmpty
    private List<MuhasebeKalemDto> kalemler; // BORÇ/ALACAK XOR + toplamlar eşit

    /**
     * İstersen özel tür
     */
    private HareketTuru hareketTuru = HareketTuru.MAHSUP;

}
