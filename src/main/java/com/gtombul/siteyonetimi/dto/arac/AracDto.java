package com.gtombul.siteyonetimi.dto.arac;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
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
public class AracDto extends BaseDto {

    private String marka;
    private Long kisiId;
    private String model;
    private String renk;
    private Integer yil;
    private String plaka;
    private String aciklama;

}
