package com.gtombul.siteyonetimi.dto.dokuman;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DokumanVersiyonDto extends BaseDto {
    private Long dokumanId;
    private Integer no;
    private String aciklama;
    private Long dosyaId;
    private String dosyaAdi;
    private String dosyaUrl;
    private String icerikTuru;
}