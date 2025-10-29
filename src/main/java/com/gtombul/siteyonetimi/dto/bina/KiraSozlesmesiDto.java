package com.gtombul.siteyonetimi.dto.bina;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class KiraSozlesmesiDto extends BaseDto {

    private Long daireId;
    private Long kiraciId;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi; // açık uçlu olabilir
    private BigDecimal kiraBedeli;
    private BigDecimal depozito;
    private String paraBirimi; // TRY, USD, EUR

}
