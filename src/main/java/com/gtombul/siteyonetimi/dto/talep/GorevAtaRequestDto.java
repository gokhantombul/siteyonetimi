package com.gtombul.siteyonetimi.dto.talep;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record GorevAtaRequestDto(
        @NotNull(message = "Atanacak personel seçimi zorunludur.")
        UUID personelUuid,

        @NotBlank(message = "Yönetici notu boş olamaz.")
        String yoneticiNotu,

        @FutureOrPresent(message = "Planlanan başlangıç tarihi geçmiş bir tarih olamaz.")
        LocalDateTime planlananBaslangicTarihi,

        @FutureOrPresent(message = "Planlanan bitiş tarihi geçmiş bir tarih olamaz.")
        LocalDateTime planlananBitisTarihi
) {}