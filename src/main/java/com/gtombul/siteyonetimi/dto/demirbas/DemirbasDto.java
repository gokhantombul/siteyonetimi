package com.gtombul.siteyonetimi.dto.demirbas;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DemirbasDto extends BaseDto {

    @NotBlank
    @Size(max = 255)
    private String ad;

    @Size(max = 128)
    private String barkod;

    @Size(max = 128)
    private String etiketNo;

    @NotNull
    private Long kategoriId;
    private String kategoriAd;

    @NotNull
    private Long depoId;
    private String depoAd;

    @NotNull
    @Digits(integer = 16, fraction = 3)
    private BigDecimal miktar;

    @NotBlank
    @Size(max = 32)
    private String birim;

    @Digits(integer = 16, fraction = 3)
    private BigDecimal minimumSeviye;

    @Size(max = 1024)
    private String aciklama;

}