package com.gtombul.siteyonetimi.dto.kargo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KargoOlusturGuncelleDto(
        @NotBlank String kargoSirketi,
        @NotBlank String takipNo,
        @NotNull Long daireId,
        @NotBlank String aliciAdSoyad,
        String aliciTelefon,
        String notlar
) {
}
