package com.gtombul.siteyonetimi.dto.demirbas;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.StokIslemTuru;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class StokHareketDto extends BaseDto {

    @NotNull
    private Long demirbasId;
    private String demirbasAd;

    @NotNull
    private Long depoId;
    private String depoAd;

    @NotNull
    private StokIslemTuru islemTuru;

    @NotNull @Digits(integer = 16, fraction = 3)
    private BigDecimal miktar;

    @Size(max = 128)
    private String referansNo;

    @Size(max = 1024)
    private String aciklama;

}
