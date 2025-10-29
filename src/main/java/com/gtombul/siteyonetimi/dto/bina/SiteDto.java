package com.gtombul.siteyonetimi.dto.bina;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SiteDto extends BaseDto {

    private String ad;
    private String kisaAd;
    private String adresSatir1;
    private String adresSatir2;
    private String mahalle;
    private String postaKodu;
    private String ilAd;
    private String ilceAd;

}
