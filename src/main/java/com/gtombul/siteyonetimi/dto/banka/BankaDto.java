package com.gtombul.siteyonetimi.dto.banka;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class BankaDto extends BaseDto {

    @NotBlank(message = "Banka adı boş olamaz")
    @Size(max = 1024, message = "Banka adı 1024 karakteri geçemez")
    private String ad;

    @NotBlank(message = "Merkez adres boş olamaz")
    @Size(max = 2048, message = "Adres 2048 karakteri geçemez")
    private String merkezAdres;

    @NotBlank(message = "Telefon boş olamaz")
    private String telefon;

    @NotNull(message = "Merkez adres ilçe boş olamaz")
    private IlceDto merkezAdresIlce;

    @Size(max = 1024, message = "Açıklama en fazla 1024 karakter olabilir.")
    private String aciklama;

    @Min(value = 10000, message = "Posta kodu 5 haneli olmalı")
    @Max(value = 99999, message = "Posta kodu 5 haneli olmalı")
    private Integer postaKod;

}
