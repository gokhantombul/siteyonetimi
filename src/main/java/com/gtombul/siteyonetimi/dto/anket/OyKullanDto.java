package com.gtombul.siteyonetimi.dto.anket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OyKullanDto {

    @NotNull
    private Long anketId;

    // ANKET türünde zorunlu
    private Long secenekId;

    // KARAR türünde zorunlu ("EVET" | "HAYIR" | "CEKIMSER")
    private String kararOyu;

    @NotNull
    private Long kisiId;

    private Long daireId;

}
