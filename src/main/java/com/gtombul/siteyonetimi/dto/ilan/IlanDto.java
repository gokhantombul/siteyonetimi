package com.gtombul.siteyonetimi.dto.ilan;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.ilan.IlanDurum;
import com.gtombul.siteyonetimi.model.ilan.IlanOnayDurumu;
import com.gtombul.siteyonetimi.model.ilan.IlanTuru;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlanDto extends BaseDto {
    private String baslik;
    private String aciklama;
    private IlanTuru turu;
    private Long kategoriId;
    private String kategoriAd;
    private BigDecimal fiyat;
    private IlanDurum ilanDurum;
    private LocalDate yayimTarihi;

    private IlanOnayDurumu onayDurumu;
    private Long onaylayanKisiId;
    private LocalDateTime onayTarihi;
    private String reddetmeNedeni;

    private Long olusturanKisiId;
    private String olusturanKisiAdSoyad;

    private List<String> resimUrlListesi;
}

