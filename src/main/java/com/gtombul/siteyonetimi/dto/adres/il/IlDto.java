package com.gtombul.siteyonetimi.dto.adres.il;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlDto extends BaseDto {

//    public IlDto(String ad, Integer plakaKodu, Integer telefonKodu) {
//        this.ad = ad;
//        this.plakaKodu = plakaKodu;
//        this.telefonKodu = telefonKodu;
//        this.ilceler = new ArrayList<>();
//    }

    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 1, max = 255, message = "Ad 255 karakteri geçemez")
    private String ad;

    @NotNull(message = "Plaka Kodu boş olamaz")
    @Min(value = 1, message = "Plaka kodu 1 ile 81 arasında olmalı")
    @Max(value = 81, message = "Plaka kodu 1 ile 81 arasında olmalı")
    private Integer plakaKodu;

    @NotNull(message = "Telefon Kodu boş olamaz")
    @Digits(integer = 3, fraction = 0, message = "Telefon Kodu 3 haneli olmalı")
    @Min(value = 100, message = "Telefon Kodu 100-999 aralığında olmalı")
    @Max(value = 999, message = "Telefon Kodu 100-999 aralığında olmalı")
    private Integer telefonKodu;

    @Valid
    private List<IlceDto> ilceler;  // <-- yeni eklendi

    // İstersen eski kısa kurucu yerine statik factory:
    public static IlDto of(String ad, Integer plakaKodu, Integer telefonKodu) {
        return IlDto.builder()
                .ad(ad)
                .plakaKodu(plakaKodu)
                .telefonKodu(telefonKodu)
                .build();
    }

}
