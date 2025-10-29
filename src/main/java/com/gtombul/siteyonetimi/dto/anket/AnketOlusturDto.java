package com.gtombul.siteyonetimi.dto.anket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AnketOlusturDto {

    @NotBlank
    @Size(max = 255)
    private String baslik;

    @Size(max = 2000)
    private String aciklama;

    @NotNull
    private String tur; // "ANKET" | "KARAR"

    private boolean anonimMi;

    @NotNull
    private LocalDateTime baslangicTarihi;

    @NotNull
    private LocalDateTime bitisTarihi;

    // ANKET türünde zorunlu, KARAR’da boş gelebilir
    private List<@NotBlank @Size(max = 500) String> secenekMetinleri;

}
