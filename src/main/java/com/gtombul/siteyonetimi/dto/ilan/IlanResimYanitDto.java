package com.gtombul.siteyonetimi.dto.ilan;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class IlanResimYanitDto extends BaseDto {

    private Long kaynakKayitId;
    private String url;
    private Integer siraNo;

}
