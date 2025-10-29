package com.gtombul.siteyonetimi.dto.bina;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.DaireTipi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DaireDto extends BaseDto {

    private Long blokId;
    private Long katId;
    private String no;
    private String bagimsizBolumNo;
    private DaireTipi tip;
    private Integer metrekare;
    private Integer odaSayisi;

}
