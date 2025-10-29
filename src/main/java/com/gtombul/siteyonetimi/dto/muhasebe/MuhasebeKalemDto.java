package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * MuhasebeKalem için DTO.
 * - BORÇ/ALACAK XOR kuralı (@AssertTrue)
 * - Döviz alanları (varsa) tutarlı olmalı (@AssertTrue)
 * - Mutabakat alanları opsiyonel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MuhasebeKalemDto extends BaseDto {

    /** Zorunlu: hareketin ait olduğu hesap */
    @NotNull
    private Long hesapId;

    /** BORÇ (TRY) — ALACAK null olmalı */
    @DecimalMin("0.00")
    private BigDecimal borcTutar;

    /** ALACAK (TRY) — BORÇ null olmalı */
    @DecimalMin("0.00")
    private BigDecimal alacakTutar;

    /** Satır açıklaması */
    private String aciklama;

    /* -------------------- Döviz opsiyonelleri -------------------- */

    /** Döviz cinsi (örn. USD/EUR). Entity'de String tutuluyor. */
    private String dovizCinsi;

    /** Döviz tutarı (hesabın para birimi cinsinden) */
    @DecimalMin("0.000000")
    private BigDecimal dovizTutar;

    /** Kullanılan kur (1 döviz = kur TRY) */
    @DecimalMin("0.000000")
    private BigDecimal kur;

    /* -------------------- Banka mutabakatı alanları -------------------- */

    /** Dış referans (banka işlem referansı vb.) */
    private String mutabakatRef;

    /** Mutabakat tarihi */
    private LocalDate mutabakatTarih;

    /* -------------------- Basit doğrulamalar -------------------- */

    /** BORÇ/ALACAK XOR: ikisinden sadece biri dolu ve sıfırdan büyük olmalı. */
    @AssertTrue(message = "Borç ve Alacak alanlarından yalnızca biri dolu olmalıdır.")
    public boolean isBorcAlacakXor() {
        boolean borcDolu   = borcTutar   != null;
        boolean alacakDolu = alacakTutar != null;
        return borcDolu ^ alacakDolu;
    }

    /** Döviz alanları tutarlılığı: herhangi biri doluysa hepsi dolu olmalı. */
    @AssertTrue(message = "Döviz işlemi için dovizCinsi, dovizTutar ve kur birlikte doldurulmalıdır.")
    public boolean isDovizTutarliligi() {
        boolean any = (dovizCinsi != null && !dovizCinsi.isBlank()) || dovizTutar != null || kur != null;
        boolean all = (dovizCinsi != null && !dovizCinsi.isBlank()) && dovizTutar != null && kur != null;
        return !any || all;
    }

}
