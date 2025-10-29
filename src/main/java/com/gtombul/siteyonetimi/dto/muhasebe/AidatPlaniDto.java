package com.gtombul.siteyonetimi.dto.muhasebe;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.HesaplamaTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AidatPlaniDto extends BaseDto {

    @NotBlank
    private String ad;

    @NotNull
    private HesaplamaTipi hesaplamaTipi;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal tutar;

    @NotNull
    private ParaBirimi paraBirimi;

    @NotNull
    @Min(1)
    @Max(28)
    private Integer vadeGunu;

    private BigDecimal gecikmeOraniGunluk;

    private String blokKodu;// null=tümü

}
