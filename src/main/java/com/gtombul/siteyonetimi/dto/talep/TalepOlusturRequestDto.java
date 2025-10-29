package com.gtombul.siteyonetimi.dto.talep;

import com.gtombul.siteyonetimi.model.enums.TalepOncelik;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

// Yeni talep oluşturmak için yalın bir DTO
public record TalepOlusturRequestDto(
        @NotBlank(message = "Konu boş olamaz.")
        @Size(min = 5, max = 200)
        String konu,

        @NotBlank(message = "Açıklama boş olamaz.")
        @Size(min = 10)
        String aciklama,

        @NotNull(message = "Öncelik seçimi zorunludur.")
        TalepOncelik talepOncelik, // Enum'un "value" değeri ("ACIL", "ORTA" vs.) gelecek

        @NotNull(message = "Kategori seçimi zorunludur.")
        UUID kategoriUuid,

        @NotNull(message = "Daire seçimi zorunludur.")
        UUID daireUuid
) {}