package com.gtombul.siteyonetimi.dto.bina;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.OturumTipi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DaireOturumDto extends BaseDto {
    private Long daireId;
    private Long kisiId;
    private OturumTipi oturumTipi;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private Long kiraSozlesmesiId; // nullable
}
