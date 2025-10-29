package com.gtombul.siteyonetimi.dto.ilan;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.ilan.IlanDurum;
import com.gtombul.siteyonetimi.model.ilan.IlanTuru;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlanListelemeDto extends BaseDto {
    private String baslik;
    private IlanTuru turu;
    private Long kategoriId;
    private String kategoriAd;
    private BigDecimal fiyat;
    private IlanDurum ilanDurum;
    private LocalDate yayimTarihi;
    private String ilkResimUrl;
}

