package com.gtombul.siteyonetimi.dto.adres.ilce;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlceDto extends BaseDto {

    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 255, message = "Ad 255 karakteri geçemez")
    private String ad;

    @NotNull(message = "İl id boş geçilemez")
    @Positive(message = "İl id pozitif olmalı")
    private Long ilId;

    private String ilAd;

    private Integer ilPlakaKodu;
    private Integer ilTelefonKodu;

    public IlceDto(Long id) {
        this.setId(id); // BaseDto’dan geliyor
    }

    public static IlceDto ofId(Long id) {
        return new IlceDto(id);
    }

    // İstersen kullanışlı bir factory:
    public static IlceDto of(String ad, Long ilId) {
        return IlceDto.builder()
                .ad(ad)
                .ilId(ilId)
                .build();
    }

}
