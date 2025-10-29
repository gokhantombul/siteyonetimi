package com.gtombul.siteyonetimi.dto.insan;

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
public class KisiDto extends BaseDto {
    private String ad;
    private String soyad;
    private String tcKimlikNo;
    private String telefon;
    private String eposta;
}