package com.gtombul.siteyonetimi.dto.muhasebe;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VirmanRequestDto {

    @NotNull
    private Long kaynakHesapId;  // ALACAK

    @NotNull
    private Long hedefHesapId;   // BORÇ

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal tutar;

    private String aciklama;

    /**
     * Idempotency için opsiyonel; dolu ise fisNo = "VRM-"+idemKey
     */
    private String idemKey;

}
