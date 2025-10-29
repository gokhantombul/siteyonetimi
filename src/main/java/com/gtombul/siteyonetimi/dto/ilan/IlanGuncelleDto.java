package com.gtombul.siteyonetimi.dto.ilan;

import com.gtombul.siteyonetimi.model.ilan.IlanDurum;
import com.gtombul.siteyonetimi.model.ilan.IlanTuru;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class IlanGuncelleDto {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String baslik;

    @Size(max = 4000)
    private String aciklama;

    @NotNull
    private IlanTuru turu;

    @NotNull
    private Long kategoriId;

    @PositiveOrZero
    private BigDecimal fiyat;

    @NotNull
    private IlanDurum ilanDurum;

    /** İlanda kalacak resimlerin kaynak id tam seti (sıra bu sıraya göre) */
    private List<Long> resimKaynakIdListesi;

}
