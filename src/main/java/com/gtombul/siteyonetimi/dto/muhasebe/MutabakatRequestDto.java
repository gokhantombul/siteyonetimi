package com.gtombul.siteyonetimi.dto.muhasebe;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MutabakatRequestDto {

    @NotNull
    private Long bankaHesapId;

    @NotNull
    private List<BankaHareketGirdi> hareketler;
    /**
     * gün toleransı (örn. 3 gün) ve tutar toleransı (örn. 0.50 TL)
     */
    private Integer gunEsneklik = 3;

    private BigDecimal tutarEsneklik = new BigDecimal("0.50");

}
