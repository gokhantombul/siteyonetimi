package com.gtombul.siteyonetimi.dto.personel;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.PersonelGorev;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PersonelDto extends BaseDto {

    @NotNull(message = "Kişi id boş olamaz")
    @Positive(message = "Kişi id pozitif olmalı")
    private Long kisiId;

    private String kisiAdSoyad; // read-only projeksiyon

    @NotNull(message = "Görev boş olamaz")
    private PersonelGorev gorev;

    @NotNull(message = "İşe giriş tarihi boş olamaz")
    private LocalDate iseGirisTarihi;

    private LocalDate iseCikisTarihi;

    @Digits(integer = 16, fraction = 2, message = "Maaş formatı hatalı")
    @PositiveOrZero(message = "Maaş negatif olamaz")
    private BigDecimal maas;

    @Size(max = 1024, message = "Açıklama 1024 karakteri geçemez")
    private String aciklama;

    // Sunum kolaylığı:
    private Boolean calisiyor;

}

