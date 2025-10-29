package com.gtombul.siteyonetimi.dto.bina;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.FirmaTuru;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class FirmaDto extends BaseDto {

    @NotBlank(message = "Firma unvanı boş olamaz")
    @Size(max = 2048, message = "Firma unvanı en fazla 2048 karakter olabilir")
    private String firmaUnvani;

    @NotBlank(message = "Kısa ad boş olamaz")
    @Size(max = 1024, message = "Kısa ad en fazla 1024 karakter olabilir")
    private String kisaAd;

    @NotBlank(message = "Adres boş olamaz")
    @Size(max = 2048, message = "Adres en fazla 2048 karakter olabilir")
    private String adres;

    @Size(max = 50, message = "Telefon en fazla 50 karakter olabilir")
    private String telefon;

    @Size(max = 2048, message = "İnternet adresi en fazla 2048 karakter olabilir")
    @Pattern(
            regexp = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$",
            message = "Geçerli bir internet adresi giriniz"
    )
    private String internetAdresi;

    @NotNull(message = "Firma türü boş olamaz")
    private FirmaTuru tur;

    @Size(max = 50, message = "Faks en fazla 50 karakter olabilir")
    private String faks;

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 1024, message = "E-posta en fazla 1024 karakter olabilir")
    private String email;

    @Size(max = 1024, message = "VKN en fazla 1024 karakter olabilir")
    private String vkn;

    @Size(max = 1024, message = "Vergi dairesi en fazla 1024 karakter olabilir")
    private String vergiDairesi;

    @Size(max = 2048, message = "Açıklama en fazla 2048 karakter olabilir")
    private String aciklama;

    private IlceDto ilce;

}
