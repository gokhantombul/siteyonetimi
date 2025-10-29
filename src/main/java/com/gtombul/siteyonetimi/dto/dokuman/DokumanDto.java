package com.gtombul.siteyonetimi.dto.dokuman;

import com.gtombul.siteyonetimi.dto.base.BaseDto;
import com.gtombul.siteyonetimi.model.enums.DokumanGorunurluk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true) Eger Id ve UUID vs gelmesin isityorsak
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class DokumanDto extends BaseDto {
    private String ad;
    private Long kategoriId;
    private String kategoriAd;
    private DokumanGorunurluk gorunurluk;
    private Map<String, String> etiketler;
    private Integer aktifVersiyonNo;
}
