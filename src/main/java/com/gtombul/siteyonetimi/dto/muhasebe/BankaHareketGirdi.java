package com.gtombul.siteyonetimi.dto.muhasebe;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BankaHareketGirdi {

    @NotNull
    private LocalDate tarih;

    @NotNull
    private BigDecimal tutar;     // ABS(TRY)

    @NotNull
    private Boolean girenMi;      // true: para girişi, false: çıkış

    private String aciklama;

    private String disRef;                 // bankanın referansı (eşleşme anahtarı)

}
