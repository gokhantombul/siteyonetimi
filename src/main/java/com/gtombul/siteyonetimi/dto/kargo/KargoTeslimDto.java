package com.gtombul.siteyonetimi.dto.kargo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KargoTeslimDto(
        @NotNull Long id,
        @Size(min = 2, max = 255) String teslimAlan,
        @Size(max = 16) String teslimKodu
) {
}
