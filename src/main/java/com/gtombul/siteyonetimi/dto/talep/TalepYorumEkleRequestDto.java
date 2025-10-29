package com.gtombul.siteyonetimi.dto.talep;

import jakarta.validation.constraints.NotBlank;

public record TalepYorumEkleRequestDto(
        @NotBlank(message = "Açıklama (yorum) boş olamaz.")
        String aciklama
) {}