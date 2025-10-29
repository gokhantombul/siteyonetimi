package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.model.enums.TalepDurum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TalepDurumGuncelleRequestDto(
        @NotNull(message = "Yeni durum boş olamaz.")
        TalepDurum yeniDurum, // "ISLEMDE" gibi value gelecek

        @NotBlank(message = "Durum güncelleme açıklaması boş olamaz.")
        String aciklama
) {}